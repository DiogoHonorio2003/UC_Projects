# Author: Marco Simoes
# Adapted from Java's implementation of Rui Pedro Paiva
# Teoria da Informacao, LEI, 2022

import sys
from huffmantree import HuffmanTree
import numpy as np


class GZIPHeader:
	''' class for reading and storing GZIP header fields '''

	ID1 = ID2 = CM = FLG = XFL = OS = 0
	MTIME = []
	lenMTIME = 4
	mTime = 0

	# bits 0, 1, 2, 3 and 4, respectively (remaining 3 bits: reserved)
	FLG_FTEXT = FLG_FHCRC = FLG_FEXTRA = FLG_FNAME = FLG_FCOMMENT = 0   
	
	# FLG_FTEXT --> ignored (usually 0)
	# if FLG_FEXTRA == 1
	XLEN, extraField = [], []
	lenXLEN = 2
	
	# if FLG_FNAME == 1
	fName = ''  # ends when a byte with value 0 is read
	
	# if FLG_FCOMMENT == 1
	fComment = ''   # ends when a byte with value 0 is read
		
	# if FLG_HCRC == 1
	HCRC = []
		
		
	
	def read(self, f):
		''' reads and processes the Huffman header from file. Returns 0 if no error, -1 otherwise '''

		# ID 1 and 2: fixed values
		self.ID1 = f.read(1)[0]  
		if self.ID1 != 0x1f: return -1 # error in the header
			
		self.ID2 = f.read(1)[0]
		if self.ID2 != 0x8b: return -1 # error in the header
		
		# CM - Compression Method: must be the value 8 for deflate
		self.CM = f.read(1)[0]
		if self.CM != 0x08: return -1 # error in the header
					
		# Flags
		self.FLG = f.read(1)[0]
		
		# MTIME
		self.MTIME = [0]*self.lenMTIME
		self.mTime = 0
		for i in range(self.lenMTIME):
			self.MTIME[i] = f.read(1)[0]
			self.mTime += self.MTIME[i] << (8 * i) 				
						
		# XFL (not processed...)
		self.XFL = f.read(1)[0]
		
		# OS (not processed...)
		self.OS = f.read(1)[0]
		
		# --- Check Flags
		self.FLG_FTEXT = self.FLG & 0x01
		self.FLG_FHCRC = (self.FLG & 0x02) >> 1
		self.FLG_FEXTRA = (self.FLG & 0x04) >> 2
		self.FLG_FNAME = (self.FLG & 0x08) >> 3
		self.FLG_FCOMMENT = (self.FLG & 0x10) >> 4
					
		# FLG_EXTRA
		if self.FLG_FEXTRA == 1:
			# read 2 bytes XLEN + XLEN bytes de extra field
			# 1st byte: LSB, 2nd: MSB
			self.XLEN = [0]*self.lenXLEN
			self.XLEN[0] = f.read(1)[0]
			self.XLEN[1] = f.read(1)[0]
			self.xlen = self.XLEN[1] << 8 + self.XLEN[0]
			
			# read extraField and ignore its values
			self.extraField = f.read(self.xlen)
		
		def read_str_until_0(f):
			s = ''
			while True:
				c = f.read(1)[0]
				if c == 0: 
					return s
				s += chr(c)
		
		# FLG_FNAME
		if self.FLG_FNAME == 1:
			self.fName = read_str_until_0(f)
		
		# FLG_FCOMMENT
		if self.FLG_FCOMMENT == 1:
			self.fComment = read_str_until_0(f)
		
		# FLG_FHCRC (not processed...)
		if self.FLG_FHCRC == 1:
			self.HCRC = f.read(2)
			
		return 0
			



class GZIP:
	''' class for GZIP decompressing file (if compressed with deflate) '''

	gzh = None
	gzFile = ''
	fileSize = origFileSize = -1
	numBlocks = 0
	f = None
	
	

	bits_buffer = 0
	available_bits = 0		

	
	def __init__(self, filename):
		self.gzFile = filename
		self.f = open(filename, 'rb')
		self.f.seek(0,2)
		self.fileSize = self.f.tell()
		self.f.seek(0)

		
	

	def decompress(self):
		''' main function for decompressing the gzip file with deflate algorithm '''
		
		numBlocks = 0

		# get original file size: size of file before compression
		origFileSize = self.getOrigFileSize()
		print(origFileSize)
		
		# read GZIP header
		error = self.getHeader()
		if error != 0:
			print('Formato invalido!')
			return
		
		# show filename read from GZIP header
		print(self.gzh.fName)
		
		
		# MAIN LOOP - decode block by block
		BFINAL = 0	
		while not BFINAL == 1:	
			
			BFINAL = self.readBits(1)
							
			BTYPE = self.readBits(2)					
			if BTYPE != 2:
				print('Error: Block %d not coded with Huffman Dynamic coding' % (numBlocks+1))
				return
			
			def Ex1_2(self):
				HLIT = self.readBits(5) #leitura do HLIT
				HLIT = HLIT + 257
				HDIST = self.readBits(5) #leitura do HDIST
				HDIST = HDIST + 1
				HCLEN = self.readBits(4) #leitura do HCLEN
				HCLEN = HCLEN + 4
				ordem_list = [16,17,18,0,8,7,9,6,10,5,11,4,12,3,14,1,15] #ordem fornecida no enunciado
				ordem = np.array(ordem_list)
				clens = np.zeros(19, dtype=np.int16)
				for i in range(HCLEN):
					clens[ordem[i]] = self.readBits(3) # leitura das sequências de 3 bits 
				return HLIT, HDIST, HCLEN, clens

			
			HLIT, HDIST, HCLEN, clens = Ex1_2(self)
			
			print("\n..................................................................................\n")
			print("HLIT =",HLIT,'\n')
			print("..................................................................................\n")
			print("HDIST =",HDIST,'\n')
			print("..................................................................................\n")
			print("HCLEN =", HCLEN,'\n')
			print("..................................................................................\n")
			print("Comprimentos dos códigos com base em HCLEN:",clens,'\n')
			print("..................................................................................\n")
			
			def Ex3(self,clens,HCLEN):
				clens = list(clens)
				
				bl_count = np.zeros(HCLEN, dtype=np.int16) #criação do bl_count que é o numero de simbolos a serem codificados com i bits
				for i in range(len(clens)):
					bl_count[clens[i]] += 1
				
				code = 0 #codigo base
				bl_count[0]=0
				next_code = np.zeros(HCLEN, dtype=np.int16)
				for i in range(1,HCLEN):
					code = (code + bl_count[i-1]) << 1 
					next_code[i] = code
			
				   
				tree_code = np.zeros(len(clens), dtype=np.int16)
				for i in range(len(clens)):
					length = clens[i]
					if(length != 0):
						tree_code[i] = next_code[length]
						next_code[length] += 1
					
				string = list()
				
				for i in range(len(clens)):
					huff = list()
					if clens[i] != 0:
						value = tree_code[i]                    # valor que se encontra na folha da arvore de huffman
						while(value > 0):                       # enquanto o valor não for zero irá percorrer o valor encontrando os 0's ou 1's
							huff.insert(0, value%2)             # verifica se o bit menos significativo é um "1" ou um "0"
							value = value//2                    # e faz uma espécie shift para a direita
						while(len(huff) < clens[i]):            # caso o valor do comprimento ser maior que o próprio código de Huffman
							huff.insert(0,0)                    # adicionar 0's a esquerda de modo a que o comprimento do codigo de huffman seja o correto
						string.append("".join(map(str,huff)))
						print(i, string[-1])                    # print do simbolo e do código de Huffman
					else:
						string.append("")     # caso comprimento ser 0
				for i in range(len(clens)):
					HCLEN_tree = HuffmanTree()   # criar árvore
					for i in range(len(clens)):  
						if string[i] != "":
							HCLEN_tree.addNode(string[i], i)   #adicionar nós da árvore  
				return HCLEN_tree
			
			print("Conversão dos comprimentos dos códigos em códigos de Huffman.\n")
			HCLEN_tree = Ex3(self, clens, HCLEN)
			print("\n..................................................................................\n")
			
			def Ex4_5(self,HCLEN_tree,comp_vet):  #Funcional para HDIST e para HLIT basta trocar a variavel "comp_vet"
				Hlen = np.zeros(comp_vet, dtype=np.int16)
			
				i=0
				HCLEN_tree.resetCurNode()
				while (i < comp_vet):
					bit = str(self.readBits(1))
					bit = HCLEN_tree.nextNode(bit)
					if bit == -2:
						continue
					elif bit >= 0: # codigos de 0 a 15 representam-se da mesma forma
						if bit==16:  # copiar tamanho do codigo anterior 3 a 6 vezes dependendo dos próximos 2 bits
							reWrite = self.readBits(2) + 3
							for j in range(reWrite):
								Hlen[i] = Hlen[i-1] # Repete o simbolo antes anterior ao 16
								i += 1
						elif bit==17: # copiar tamanho em 0's entre 3 a 10 vezes dependendo dos próximos 3 bits
							reWrite = self.readBits(3) + 3
							for j in range(reWrite):
								Hlen[i] = 0  #põe zeros
								i += 1
						elif bit==18:  # copiar tamanho em 0's entre 11 a 138 vezes dependendo dos próximos 7 bits
							reWrite = self.readBits(7) + 11 
							for j in range(reWrite):
								Hlen[i] = 0 #põe zeros
								i += 1
						else:
							Hlen[i] = bit
							i += 1
						HCLEN_tree.resetCurNode()
					elif bit == "":
						 pass
				return Hlen
			
			Hlen_lit_comp = Ex4_5(self, HCLEN_tree, HLIT)
			Hlen_dist = Ex4_5(self, HCLEN_tree, HDIST)
			print("Códigos de literias/comprimentos (HLIT):\n")
			print(Hlen_lit_comp,'\n')
			print("..................................................................................\n")
			print("Códigos de distâncias (HDIST):\n")
			print(Hlen_dist,'\n')
			print("..................................................................................\n")
			
			#ex 6
			#criação dos códigos de huffman de ambos os alfabetos criados na função "Ex4_5"
			#uso da função "Ex3" para criação dos códigos de huffman
			print("Códigos de Huffman do alfabeto dos literais/comprimentos\n")
			HLIT_tree = Ex3(self, Hlen_lit_comp, HLIT)
			print("\n..................................................................................\n")
			print("Códigos de Huffman do alfabeto das distâncias")
			HDIST_tree = Ex3(self, Hlen_dist, HDIST)
			print("\n..................................................................................\n")

			
			def comp_decode(self,value):  
				if 0 <= value <= 264: # caso o valor seja menor que 265 então o comprimento é igual ao seu valor menos 254
					comp = value - 254
				elif value == 285: # caso o valor seja 285 o seu comprimento também é 258
					comp = 258
				# caso o valor seja superior ou igual a 265 e inferior ou igual a 284, o valor irá possuir bits extra
				# o numero de bits extra irá variar entre 1 e 5 
				else:
					bit = ((value-265)//4) + 1 #numero de bits extra
					comp = 11 #numero minimo de comprimento que um valor com bits extra irá possuir 
					
					# soma do comprimento dependendo do número de bits extra que o valor possui
					#EX:(value = 271) então os bits extra são 2 pois ((271-265)/4)+1 = 2,5 visto que é um inteiro é igual a 2
					#comp = 11 + ((2**1)*4) = 11 + 8 = 19
					for i in range(1,bit): 
						comp = comp + (2**(i))*4
						
					# verificação em qual das 4 linhas se encontra
					# EX: (271 - 265) % 4 == 2 -> linha 3
					# comp = 19 + 2**2 + 2**2 = 19+4+4 = 27 
					for i in range(1, ((value - 265) % 4) + 1):
						comp = comp + 2 ** bit
						
					
					lerBit = self.readBits(bit)
					comp = comp + lerBit # soma dos bits extra
				return comp
			
			
			def dist_decode(self,value):
				if 0 <= value <= 3: # caso o valor seja menor que 4 então a distância é igual ao valor mais 1
					dist = value + 1
				# caso o valor seja superior ou igual a 3 e inferior ou igual a 29, o valor irá possuir bits extra
				# o numero de bits extra irá variar entre 1 e 13
				else:
					bit = ((value-4)//2) + 1 #numero de bits extra
					dist = 5 #numero minimo de distância que um valor com bits extra irá possuir
					
					#soma da distância dependendo do número de bits extra que o valor possui
					#EX:(value = 6) então os bits extra são 2 pois ((6-4)/2)+1 = 2
					#dist = 5 + ((2**1)*2) = 5 + 4 = 9
					for i in range(1,bit):
						dist = dist + (2**(i)) * 2 
						
					# verificação em qual das 2 linhas se encontra
					# EX: ((6-4)%2) = 0 logo não entra no "if"
					linha = ((value - 4) % 2)
					if (linha == 1):
						dist = dist + 2 ** bit
					
					lerBit = self.readBits(bit)
					dist = dist + lerBit # soma dos bits extra
				return dist
			
			  
			def Ex7(self,HLIT_tree,HDIST_tree):
				output = list()
				while True:
					value = -2
					HLIT_tree.resetCurNode()
					while(value == -2):
						value = str(self.readBits(1))   #obtem valor da árvore de literais/comprimentos      
						value = HLIT_tree.nextNode(value)
					if value == -1:
						HLIT_tree.resetCurNode()
						pass
					if value < 256:  #copia o valor para a lista pois o valor é menor que 256
						output.append(value)
					elif value == 256: #fim do bloco
						break
					else: # no caso do valor se encontrar no intervalo 257 - 285
						#descodificar o comprimento
						comp = comp_decode(self, value)    
						if comp == -1:
							print("ERRO: nao foi possivel descodificar o comprimento")
							break
						value = -2
						HDIST_tree.resetCurNode()
						while(value == -2):	
							value = str(self.readBits(1))   #obtem o valor da árvore das distâncias
							value = HDIST_tree.nextNode(value) 
						if value == -1:
							HDIST_tree.resetCurNode()
							break
						# descodificar a distância
						dist = dist_decode(self, value)
						if dist == -1:
							print("ERRO: nao foi possivel descodificar a distancia")
							break
						
						# recua a distancia no "output" e copia comprimento bytes a partir da posição que a distância fez recuar 
						for i in range(comp):
							val = output[-dist]
							output.append(val)
				return np.array(output)  #guarda num array	
			
			descomp_compactdata = Ex7(self, HLIT_tree, HDIST_tree)
			print("Descompactação dos dados comprimidos:\n")
			print(descomp_compactdata)
			print("\n..................................................................................\n")
			
			
			#ex8
			# abrir ficheiro de escrita 
			self.ficheiro = open(self.gzh.fName,'w')
			for i in descomp_compactdata:
				self.ficheiro.write(chr(i))  #escreve os carateres que se encontram no array
			self.ficheiro.close()
			
			# update number of blocks read
			numBlocks += 1
		# close file		
				
			
		self.f.close()	
		print("End: %d block(s) analyzed." % numBlocks)

	
	def getOrigFileSize(self):
		''' reads file size of original file (before compression) - ISIZE '''
		
		# saves current position of file pointer
		fp = self.f.tell()
		
		# jumps to end-4 position
		self.f.seek(self.fileSize-4)
		
		# reads the last 4 bytes (LITTLE ENDIAN)
		sz = 0
		for i in range(4): 
			sz += self.f.read(1)[0] << (8*i)
		
		# restores file pointer to its original position
		self.f.seek(fp)
		
		return sz		
	

	
	def getHeader(self):  
		''' reads GZIP header'''

		self.gzh = GZIPHeader()
		header_error = self.gzh.read(self.f)
		return header_error
		

	def readBits(self, n, keep=False):
		''' reads n bits from bits_buffer. if keep = True, leaves bits in the buffer for future accesses '''

		while n > self.available_bits:
			self.bits_buffer = self.f.read(1)[0] << self.available_bits | self.bits_buffer
			self.available_bits += 8
		
		mask = (2**n)-1
		value = self.bits_buffer & mask

		if not keep:
			self.bits_buffer >>= n
			self.available_bits -= n

		return value

	


if __name__ == '__main__':

	# gets filename from command line if provided
	filename = "FAQ.txt.gz"
	if len(sys.argv) > 1:
		fileName = sys.argv[1]			

	# decompress file
	gz = GZIP(filename)
	gz.decompress()

	
	