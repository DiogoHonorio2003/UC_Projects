from scipy.io import wavfile
import numpy as np
import matplotlib.pyplot as plt
import matplotlib.image as mimg
import huffmancodec as huff


#dependente da informaçao recebida produz um alfabeto adequado
#guarda tudo num array  do numpy com apenas uma linha

def info(fileName):
    #SOM   
    if "wav" in fileName:
        
        [fs,data] = wavfile.read(fileName)
        
        #valores possiveis sao de 0-255 np.arrange(256)
        alfabetoList=np.arange(256)
    #TEXTO
    elif "txt" in fileName: 
        #abre o ficheiro e lê
        with open (fileName, 'r') as file:
            texto = list(file.read())

            #transforma as letras em numeros
            #Aqui restringimos só às letras ASCII A -> Z e a -> z
            for i in range (len(texto)):
                texto[i] = ord(texto[i])
            data = np.asarray(texto) 
            data = data[data >= 65]
            data = data[data <= 122] 
                
            #alfabeto e letras a-z + A-Z em numeros (ASCII) 
            alfabetoList = ([(i) for i in range(ord('A'),ord('Z')+1)] + [(i) for i in range(ord('a'),ord('z')+1)])
            alfabetoList = np.asarray([alfabetoList]).flatten()
        
    #IMAGEM
    else:
        data = mimg.imread(fileName)
        data=data.flatten()
        alfabetoList = np.arange(256)
            
    return [alfabetoList,data]


def alfabetoParaChar(alfabeto):
    #cria uma lista vazia onde adiciona valor a valor passando de ASCII a char
    alfabetoList = list()
    for i in range (len(alfabeto)):
        alfabetoList.append((chr(alfabeto[i])))
    #passa a np e retorna
    return np.asarray(alfabetoList)



def ocorrenciasTEXTO(data, alfabeto):
    #faz um array com o tamanho do alfabeto
    arrayOcorrencias = np.zeros_like(alfabeto)

    for i in range (len(alfabeto)):
        #procura os indices onde aparece determinado simbolo do alfabeto
        aux = np.where((alfabeto[i]) == data)
        #Aux fica com valores do genero (1,2,3,4,5) pois usamos o flatten()
        #ou seja basta saber o tamanho de uma das posições de aux como por exemplo aux[0] entao sabemos o numero de ocorrencias
        #conta as ocorrencias e guarda no array
        arrayOcorrencias[i] = int(len(aux[0]))
        
    return arrayOcorrencias




def ocorrencias(data,alfabeto):
    arrayOcorrencias = np.zeros_like(alfabeto)
    
    for i in range (len(data)):
        arrayOcorrencias[data[i]] += 1
        
    return arrayOcorrencias





def histograma_ocorrencias(alfabeto,arrayOcorrencias,fileName):
    #Se for texto passamos a ter as letras em si na zona do alfabeto
    if ("txt" in fileName):
        alfabeto=alfabetoParaChar(alfabeto)
        
      #faz o grafico de barras X=alfabeto,Y = ocorrencias   
    plt.figure(1)
    plt.bar(alfabeto,arrayOcorrencias)
    plt.legend()
    plt.xlabel("Alfabeto")
    plt.ylabel("Ocorrencias")
    plt.show()
    
    
    
    
    
#pergunta2
def entropia(arrayOcorrencias,data):
    valor = 0
    
    #tamanho da fonte de informaçao
    totalSimbolos = sum(arrayOcorrencias)
    probabilidade = arrayOcorrencias/totalSimbolos
    probabilidade = np.asarray(probabilidade)
    probabilidade = probabilidade[probabilidade > 0]
    #somatorio -> probabilidade * log(1/probabilidade)
    #probabilidade = numero de ocorrencias / total de simbolos na fonte de informacao
    
    valor = - np.sum(probabilidade*np.log2(probabilidade))
    
    return valor





#funçoes pergunta 4

def ocorrenciasTirandoZeros(arrayOcorrencias):
    return arrayOcorrencias[arrayOcorrencias > 0]

def mediaPonderadaHuffman(arrayOcorrencias, data):
    #ver o comprimento de cada simbolo
    codec = huff.HuffmanCodec.from_data(data)
    simbolos, comprimento = codec.get_code_len()
    #asarray
    simbolos = np.asarray(simbolos)
    comprimento = np.asarray(comprimento)
    

    #retirar as ocorrencias que são 0 porque a função retorna apenas comprimento para os simbolos que aparecem
    aux = ocorrenciasTirandoZeros(arrayOcorrencias)
    #media ponderada
    valorMediaPonderada = np.sum((aux / len(data)) * comprimento)
    
    valorVarianciaPonderada = varianciaPonderadaHuffman(comprimento, valorMediaPonderada, aux, data)
    
    return valorMediaPonderada, valorVarianciaPonderada

def varianciaPonderadaHuffman(comprimento, media, arrayOcorrencias,data):
    valorVarianciaPonderada = 0
    #Usamos a formula normal da variancia  mas ao invés de dividir pelo numero total, acabamos por multiplicar pela probabilidade
    for i in range (len(comprimento)):
        valorVarianciaPonderada = valorVarianciaPonderada + (arrayOcorrencias[i]/len(data))*((comprimento[i]-media) ** 2)
        
    return valorVarianciaPonderada

    



#funçoes pergunta 5

def agruparSimbolos(arrayAlfabeto, data, fileName):
    arrayAlfabetoAgrupado = list()

    #primeiro valor * dimensao + segundo valor = FORMULA DADA
    
    #para o vetor [0,1,2,3] vamos criar um vetor [0,0,1,1,2,2,3,3]
    #sendo o numero de repeticoes o tamanho do alfabeto inicial
    arrayAlfabetoAgrupado = np.repeat(arrayAlfabeto, len(arrayAlfabeto))
    
    #De seguida multiplicamos pela dimensao do alfabeto dado
    arrayAlfabetoAgrupado *= len(arrayAlfabeto)
    
    #Criamos agora um vetor do tipo [0,1,2,3,0,1,2,3]
    #sendo o numero de repeticoes o tamanho do alfabeto inicial
    aux = np.tile(arrayAlfabeto, len(arrayAlfabeto))
    arrayAlfabetoAgrupado += aux

    return np.asarray(arrayAlfabetoAgrupado)

def ocorrenciasAgrupamentoTXT(data, arrayAlfabeto):
    #faz um array com o tamanho do alfabeto
    arrayOcorrencias = np.zeros_like(arrayAlfabeto)

    for u in range (0, len(data) - 1, 2):
        for i in range (len(arrayAlfabeto)):
            
            #procura os indices onde aparece determinado simbolo do alfabeto
            #tambem segundo a formula simblo * dimensao + simbolo
            if (data[u] * len(data) + data[u+1] == arrayAlfabeto[i]):
                arrayOcorrencias[i] += 1
            
    return arrayOcorrencias

def ocorrenciasAgrupamento(data, arrayAlfabeto, tamanhoAlfabetoInicial):
    #faz um array com o tamanho do alfabeto
    arrayOcorrencias = np.zeros_like(arrayAlfabeto)

    for u in range (0, len(data) - 1, 2):
        #procura os indices onde aparece determinado simbolo do alfabeto
        #segundo o principio que está tudo ordenado através da forma primeiro valor * len(alfabeto) + segundo valor
        #o indice correto será o primeiro valor * 255 + segundo valor (por exemplo 255, pode ser outro)
        #assim só teremos um loop
        arrayOcorrencias[data[u] * tamanhoAlfabetoInicial + data[u+1]] += 1
    return arrayOcorrencias






#PERGUNTA 6

def informacaoMutua(query,target,alfabeto):
    probabilidadeConjunta = np.zeros((len(alfabeto),len(alfabeto)))
    
    for i in range (len(query)):
        probabilidadeConjunta[query[i]][target[i]] += 1
        
    #Formula de informaçao mutua I(X,Y) = H(X) + H(Y) - H(X,Y)
    ocorrenciasQUERY = ocorrencias(query,alfabeto)
    ocorrenciasTARGET = ocorrencias(target,alfabeto)
    #Vamos calcular a entropia de X e de Y ou seja H(X) e H(Y)
    entropiaQUERY = entropia(ocorrenciasQUERY,query)
    entropiaTARGET = entropia(ocorrenciasTARGET,target)
    #Vamos calcular agora a probabilidade conjunta, mas vamos tirar todos os 0s do array para os tamanhos coincidirem
    probabilidadeConjunta = probabilidadeConjunta / len(query)  
    probabilidadeConjunta = ocorrenciasTirandoZeros(probabilidadeConjunta)
    #Passamos entao para o calculo da entropia em si de H(X,Y)
    entropiaConjunta = - np.sum(probabilidadeConjunta * np.log2(probabilidadeConjunta)) #formula de calculo de entropia conjunta aplicada
        
    #Por fim aplicamos a formula final de informaçao mutua , I(X,Y) = H(X) + H(Y) - H(X,Y)
    informacaoMutua = entropiaQUERY + entropiaTARGET - entropiaConjunta
    return informacaoMutua
    
def percorrerOtarget(query,target,step,alfabeto):
    #criar array com os slots necessários
    vetorFinal = np.zeros(int((len(target)-len(query))/step) + 1)
        
    #criaçao de variaveis iniciais
    inicio = 0
    fim = (len(query))
    for i in range (len(vetorFinal)):   
        vetorFinal[i] = informacaoMutua(query,target[inicio:fim],alfabeto)
        #atualizamos as variaveis
        inicio += step
        fim += step
        #proteção especifica para prevenir que nao ultrapassamos o valor de comprimento final do target, pois se estamos a percorrer o target nao faz sentido continuar a incrementar quando chegamos ao final dele
        if (fim>len(target)):
            break
        
    #Vamos precisar de um array para o eixo X na altura de fazer grafico
    #fazemos entao um array com o numero de ciclos corridos que é basicamente o numero de janelas que foram deslizadas até todas as janelas do target tenham sido comparadas ao query
    lista = np.arange(len(vetorFinal)) #isto basicamente vais nos permitir enumerar as janelas por ordem seja tempo ou numero de janela no eixo do X 
    return vetorFinal, lista
    
    
    
def graficoInformacaoMutua(eixoX, eixoY):
      #faz o grafico de barras X=Janelas,Y = Informação Mutua lida
      plt.figure(1)
      plt.plot(eixoX,eixoY)
      plt.legend()
      plt.xlabel("Janela / Tempo")
      plt.ylabel("Informação Mutua")
      plt.show()
      
      
def simulador(nomeFicheiro,arrayAlfabeto,data):
    #recolher a informação de um dado ficheiro(tanto o data como o alfabeto) e analisamos em comparação com o saxriff
    [arrayAlfabetoSimulador,dataSimulador] = info(nomeFicheiro)
    
    query = data #o query vai ser o data do ficheiro saxriff
    target = dataSimulador #enquanto que o target que iremos percorrer é o ficheiro que é para ser lido
    #step vai ser de 1/4 da duração da query,logo
    step = int(0.25*len(query))
    
    #depois corremos a função de percorrer o target normalmente
    vetorSimulador,x = percorrerOtarget(query, target, step, arrayAlfabeto)
    return max(vetorSimulador)

      
      
      
      
#funçao main do projeto
        
def main():
    
    #Exercicio3 - ALTERAR NOME DO FICHEIRO
    fileName = "C:\\Gabriel\\Universidade\\TI\\DATA\\MI\\saxriff.wav"
    [arrayAlfabeto, data] = info(fileName)
    if len(data.shape) == 2:
        data = data[:, 0]
    
    
    #Exercicio1
    if 'txt' in fileName:
        arrayOcorrencias = ocorrenciasTEXTO(data, arrayAlfabeto)
    else:
        arrayOcorrencias = ocorrencias(data, arrayAlfabeto)
    histograma_ocorrencias(arrayAlfabeto, arrayOcorrencias, fileName)

    #Exercicio 2
    valorEntropia = entropia(arrayOcorrencias,data)
    print("-------------------")
    print("Ex2")
    print("O valor da entropia é de", valorEntropia, "bits")
    print("-------------------")
    
    
    #Exercicio 4
    valorMediaPonderada,  valorVarianciaPonderada = mediaPonderadaHuffman(ocorrenciasTirandoZeros(arrayOcorrencias), data)
    print("-------------------")
    print("Ex4")
    print("Huffman - Valor média ponderada: ", valorMediaPonderada)
    print("Variancia - Valor variancia ponderada: ", valorVarianciaPonderada)
    print("-------------------")
    
    #Exercicio 5
    arrayAlfabetoAgrupamento = agruparSimbolos(arrayAlfabeto, data, fileName)
    
    #duas formas diferentes para otimizar quando não é texto
    if "txt" in fileName:
        arrayOcorrenciasAgrupamento = ocorrenciasAgrupamentoTXT(data, arrayAlfabetoAgrupamento)
    else:
        arrayOcorrenciasAgrupamento = ocorrenciasAgrupamento(data, arrayAlfabetoAgrupamento, len(arrayAlfabeto))
        
    #calculo da entropia dividindo por 2 porque são agrupamentos de 2
    valorEntropiaAgrupamento = entropia(arrayOcorrenciasAgrupamento,arrayAlfabetoAgrupamento) / 2
    print("-------------------")
    print("Ex5")
    print("O valor de entropia[nos agrupamentos 2 a 2] é de", valorEntropiaAgrupamento, "bits")
    print("-------------------")
    
    #Exercicio 6
    #Apenas executar quando o ficheiro é o ficheiro saxriff.wav
    if fileName == "C:\\Gabriel\\Universidade\\TI\\DATA\\MI\\saxriff.wav":
        #Alinea a
        #Exercicio com os valores providenciados no enunciado
        if len(data.shape) == 2:
            data = data[:, 0]
        
        queryAlineaA = np.array([2,6,4,10,5,9,5,8,0,8])
        targetAlineaA = np.array([6,8,9,7,2,4,9,9,4,9,1,4,8,0,1,2,2,6,3,2,0,7,4,9,5,4,8,5,2,7,8,0,7,4,8,5,7,4,3,2,2,7,3,5,2,7,4,9,9,6])
        alfabetoAlineaA = np.array([0,1,2,3,4,5,6,7,8,9,10])
        stepAlineaA = 1
        
        vetorResultanteAlineaA = percorrerOtarget(queryAlineaA,targetAlineaA,stepAlineaA,alfabetoAlineaA)
        print("-------------------")
        print("Ex6 Alínea A")
        print("Valores da informação mutua resultantes dos dados providenciados no encunciado.")
        print(vetorResultanteAlineaA[0]) #printamos só o indice 0, pois este vetor resultante contem um array de informacaoMutua no indice 0 e uma lista para ser usado como eixoX no grafico no indice 1
        print("-------------------")
        
        #Alinea b
        #Exercicio com os valores dos ficheiros saxriff.wav e os target01 e 02
        ficheiroAuxiliar = "C:\\Gabriel\\Universidade\\TI\\DATA\\MI\\target02 - repeatNoise.wav"
        [arrayAlfabetoAlineaB,dataAlineaB] = info(ficheiroAuxiliar)# vamos ler a informaçao presente no ficheiro de som target01
        if len(dataAlineaB.shape) == 2:
            dataAlineaB = dataAlineaB[:, 0]
        #Usando como query o ficheiro saxriff  e como target o ficheiro target01 ou 02
        queryAlineaB = data #data que vem da leitura do ficheiro
        targetAlineaB = dataAlineaB #info correspondente ao ficheiro da alinea B
        stepAlineaB = int(0.25*len(queryAlineaB)) #1/4 do valor do comprimento do ficheiro query
        vetorFinal , x = percorrerOtarget(queryAlineaB,targetAlineaB,stepAlineaB,arrayAlfabetoAlineaB)
        #Vamos mostrar o grafico resultante
        graficoInformacaoMutua(x,vetorFinal)
        #Apresentamos os valores no ecrã
        print("-------------------")
        print("Ex6 Alínea B")
        print("Valores de informação mútua entre os ficheiros ",fileName," e ",ficheiroAuxiliar," .")
        print(vetorFinal)
        print("-------------------")
        
        
        
        #Alinea c
        #Fazemos uma lista para os valores maximos da infoMutua que serao retribuidos pelo simulador
        informacaoMaxMutua = list()
        
        #fazemos uma lista de apoio com os nomes dos ficheiros ordenados da song01 até à song07
        informacaoMaximaNomesFicheiros = ["C:\\Gabriel\\Universidade\\TI\\DATA\\MI\\Song01.wav","C:\\Gabriel\\Universidade\\TI\\DATA\\MI\\Song02.wav","C:\\Gabriel\\Universidade\\TI\\DATA\\MI\\Song03.wav","C:\\Gabriel\\Universidade\\TI\\DATA\\MI\\Song04.wav",
                                          "C:\\Gabriel\\Universidade\\TI\\DATA\\MI\\Song05.wav","C:\\Gabriel\\Universidade\\TI\\DATA\\MI\\Song06.wav","C:\\Gabriel\\Universidade\\TI\\DATA\\MI\\Song07.wav"] 
        
        #Vamos correr o simulador para cada ficheiro correndo a lista de apoio com os ficheiros
        for i in informacaoMaximaNomesFicheiros:
            informacaoMaxMutua.append(simulador(i, arrayAlfabeto, data)) #adicionamos o valor de cada infoMutuaMax à lista da informacaoMaxima que queremos
        
        #Usamos o zip para fazermos o dicionario com uma key informacaoMutua, ao respetivo nome do ficheiro correspondente
        #isto só dá para fazer porque ambvas as listas têm os nomes dos ficheiros por ordem
        informacaoMaximaD = dict(zip(informacaoMaxMutua, informacaoMaximaNomesFicheiros))
        print("-------------------")
        print("Ex6 Alínea C")
        print("Informação mútua máxima entre o ficheiro ",fileName," e todos os Song.wav desde 01 a 07.")
        #ordena os valores de infoMaxMutua por ordem crescente
        informacaoMaxMutua = np.sort(informacaoMaxMutua) 
        
        #Para printar vamos printar os valores correndo o infoMaxMutua ao contrario de modo a apresentar os valores por ordem decrescente no ecrã
        for i in range (len(informacaoMaxMutua)-1, -1, -1):
            print(informacaoMaximaD[informacaoMaxMutua[i]], "com informação mútua máxima:", informacaoMaxMutua[i])
        print("-------------------")
        
    
        
    
main()