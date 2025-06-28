import multiprocessing
import time
import pickle
import random
import logging
from collections import Counter

# Configuração do logging
LOG_FILE = "log.log"
logging.basicConfig(filename=LOG_FILE, level=logging.INFO, format='%(asctime)s - %(levelname)s - %(message)s')


CHECKPOINT_FILE = "checkpoint.pkl"
MAX_RETRIES = 2
inicio = time.time()



# Backward recovery checkpoint save
def save_checkpoint(data):
    with open(CHECKPOINT_FILE, "wb") as f:
        pickle.dump(data, f)
    logging.info("Checkpoint salvo com sucesso.")

# Backward recovery checkpoint load
def load_checkpoint():
    try:
        with open(CHECKPOINT_FILE, "rb") as f:
            logging.info("Checkpoint carregado com sucesso.")
            return pickle.load(f)
    except FileNotFoundError:
        logging.warning("Nenhum checkpoint encontrado.")
        return None


#def simulate_error():
    #if random.random() < 0.2:
        #logging.error("Erro crítico simulado!")
        #raise RuntimeError("Erro crítico simulado!")


def compute_averages(pressures):

    tyre_averages = []

    try:
        
        # Backward Recovery     
        save_checkpoint(pressures)
        
        # Data consistency - sem lista
        if not isinstance(pressures, list):
            logging.error(f"O argumento 'pressures' deve ser uma lista de listas.")
            raise ValueError(f"O argumento 'pressures' deve ser uma lista de listas.")
    
        # Data consistency - 1 lista
        if not all(isinstance(p, list) for p in pressures):
            logging.error("O argumento 'pressures' deve ser uma lista de listas.")
            raise ValueError("O argumento 'pressures' deve ser uma lista de listas.")
        
        # Data consistency - 4 pneus
        if len(pressures) != 4:
            logging.error("Erro de consistência: devem existir exatamente 4 pneus!")
            raise ValueError("Erro de consistência: devem existir exatamente 4 pneus!")
        
        #simulate_error()
        
        for readings in pressures:
            
            # Data consistency - Valores sem ser int/float
            if any(not isinstance(p, (int, float)) for p in readings):
                logging.error("Erro de consistência: Detetado valor não numérico nas pressões!")
                raise ValueError("Erro de consistência: Detetado valor não numérico nas pressões!")
            
            # Data consistency - Pressões negativas
            if any(p < 0 for p in readings):
                logging.error("Erro de consistência: Detetada pressão negativa!")
                raise ValueError("Erro de consistência: Detetada pressão negativa!")
            
            valid_readings = [p for p in readings if p > 0]
            
            #time.sleep(1)
            
            # Ignorar 0 PSI
            if not valid_readings:
                avg_pressure = float('inf')
            else:
                avg_pressure = sum(valid_readings) / len(valid_readings)
                
            tyre_averages.append(avg_pressure)

    except Exception as e:
        logging.error(f"Erro detectado ao calcular médias: {e}")
        raise
    
    return tyre_averages

# - TMR - Average voting
def voting(results):
    
    final_result = []
    
    for i in range(len(results[0])):
        values = [results[0][i], results[1][i], results[2][i]]
        count = Counter(values)
        majority_value, freq = count.most_common(1)[0]
        
        if freq >= 2:
            final_result.append(majority_value)
        else:
            logging.error(f"Inconsistência detectada em TMR para o pneu {i}! Nenhuma maioria encontrada.")
            raise ValueError(f"Inconsistência detectada em TMR para o pneu {i}!")
         
    return final_result

def tyre_pressure_warning(pressures, target_pressure, retries=0):
    
    try:
        
        # Data consistency - Target pressures negativas / sem ser int
        if not isinstance(target_pressure, (int, float)):
            raise ValueError("O 'target_pressure' deve ser um número (int ou float).")
    
        if target_pressure <= 0:
            raise ValueError("O 'target_pressure' deve ser um número positivo.")
        
        with multiprocessing.Pool(processes=3) as pool:
            async_results = []
            for _ in range(3):
                async_result = pool.apply_async(compute_averages, (pressures,))
                async_results.append(async_result)
            
            results = []
            for async_result in async_results:
                try:
                    result = async_result.get(timeout=1)  # 1 second timeout for each process
                    results.append(result)
                except multiprocessing.TimeoutError:
                    logging.error(f"Processo excedeu o tempo de execução de 1 segundo.")
                    raise ValueError(f"Processo excedeu o tempo de execução de 1 segundo.")
                    
        tyre_averages = voting(results)

        warning_tyres = [i for i, avg in enumerate(tyre_averages) if avg < target_pressure]
        
        return sorted(warning_tyres, key=lambda i: tyre_averages[i])
    
    # Time redundancy - Max 3 tentativas
    except Exception as e:
        logging.error(f"Erro detectado: {e}. Tentativa {retries + 1}/{MAX_RETRIES}.")
        if retries < MAX_RETRIES:
            pressures = load_checkpoint()
            if pressures:
                return tyre_pressure_warning(pressures, target_pressure, retries + 1)
        logging.critical("Número máximo de tentativas atingido. Abortando operação.")
        raise

def main(): 
    test_cases = [
    #[]
    ([[32, 32], [32, 32], [32, 32], [32, 32]], 30),

    #[0]
    ([[28, 28], [32, 32], [32, 32], [32, 32]], 30),

    #[3,1,0,2]
    ([[26, 26], [25, 25], [27, 27], [20, 20]], 30),

    #[0]
    ([[0, 0], [32, 32], [32, 32], [32, 32]], 30),

    #[2,1]
    ([[30, 31], [29, 30], [28, 27], [32, 33]], 30),

    #[ERROR]
    ([[-10, 10], [32, 32], [32, 32], [32, 32]], 30),

    #[1]
    ([[], [32, 22], [32, 32], [32, 32]], 30),

    #[3]
    ([[0, 0], [0, 0], [0, 0], [25, 25]], 30),

    #[]
    ([[30, 30], [30, 30], [30, 30], [30, 30]], 30),
    
    #[ERROR]
    ([[30, 30], [30, 30], [30, 30]], 30),
    
    #[ERROR]
    ([[30, 30], [30, 30], [30, 30], [30, 30], [30, 30]], 30),
    
    #[]
    ([[0, 0], [0, 0], [0, 0], [0, 0]], 30),
    
    #[ERROR]
    (10, 30),
    
    #[ERROR]
    ([10,10,10,10], 5),
    
    #[ERROR]
    ([[30, 30], [30, 30], [30, 30], [30, 30]], -10),
    
    #[ERROR]
    (None, 30),
    
    #[ERROR]
    ([[30, 30], [30, 30], [30, 30], [30, 30]], None),
    
    #[ERROR]
    ([[32, 32], [32, '32'], [32, 32], [32, 32]], 30), 
    
    #[ERROR]
    ([[32, 32], [32, [32]], [32, 32], [32, 32]], 30),
    
    #[]
    ([[], [], [], []], 30),
    
    #[ERROR]
    ([[{'pressure': 32}, {'pressure': 32}], [32, 32], [32, 32], [32, 32]], 30),
    
    #[0,1,2,3]
    ([[0.0001, 0.0001], [0.0001, 0.0001], [0.0001, 0.0001], [0.0001, 0.0001]], 1),
]


    for pressures, target in test_cases:
        try:
            result = tyre_pressure_warning(pressures, target)
            logging.info(f"Resultado final: {result}")
            print(f" Resultado: {result}")
        except Exception as e:
            logging.error(f"Erro detectado: {e}")
            print(f" Erro detectado: {e}")
    
    fim = time.time()
    total_time = fim - inicio
    logging.info(f"Tempo de execução total: {total_time:.5f} segundos")
    print(f"\nTempo de execução total: {total_time:.5f} segundos")
    
if __name__ == "__main__": 
    main()
