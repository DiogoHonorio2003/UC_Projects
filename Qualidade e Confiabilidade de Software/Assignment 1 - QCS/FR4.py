import random
import logging, threading
from typing import Counter

# Logger Configuration
logging.basicConfig(
    level=logging.DEBUG,                                    # Nível de log (DEBUG, INFO, WARNING, ERROR, CRITICAL)
    format="%(asctime)s - %(levelname)s - %(message)s",     # Formato da mensagem
    datefmt="%H:%M:%S",                                     # Formato da data
    handlers=[
        logging.FileHandler("FR4.log", mode="w")            # Log em arquivo
        #logging.StreamHandler()                            # Log no console
    ]
)

# Global variables
results = [None, None, None]

def verify_inputs(distances, levels):
    # Validate correct input types
    if not isinstance(distances, list) or not isinstance(levels, list):
        raise ValueError("Both distances and levels must be lists.")
    
    # Validate non-empty lists & number of sensors
    if not levels:
        raise ValueError("No beep levels defined.")
    
    if not distances or len(distances) < 2: 
        raise ValueError("Not enough sensors.")    
    
    # Filter out invalid distances
    valid_distances = [d for d in distances if isinstance(d, (int, float)) and d >= 0]
    if not valid_distances:
        raise ValueError("No valid distances.")  

    # Filter out invalid levels and keep their original indices
    valid_levels_with_indices = [(i, level) for i, level in enumerate(levels) if isinstance(level, (int, float)) and level >= 0]
    if not valid_levels_with_indices:
        raise ValueError("No valid levels.")
    
    # Extract valid levels
    valid_levels = [level for _, level in valid_levels_with_indices]

    # Extract original indices
    original_indices = [i for i, _ in valid_levels_with_indices]

    # Return valid distances, levels, and their original indices
    return valid_distances, valid_levels, original_indices


def get_BeepLevel(distances, levels, thread_id):
    # Validate inputs
    try:
        distances, levels, indices = verify_inputs(distances, levels)

    except ValueError as e:
        logging.error(f"[FUNC{thread_id}] {e}")
        results[thread_id] = -1
        return

    # Find the minimum valid distance
    min_distance = min(distances)

    # Self-error generation 20% for testing
    #if random.random() < 0.2:
    #    logging.debug(f"[FUNC{thread_id}] Generating self-error.")
    #    time.sleep(1.5)

    # Find the closest beep level
    closest_index = 0
    closest_diff = abs(min_distance - levels[0])
    for i, level in enumerate(levels):
        diff = abs(min_distance - level)
        if diff < closest_diff and level <= min_distance:
            closest_diff = diff
            closest_index = i

    logging.debug(f"[FUNC{thread_id}] Chosen level: {levels[closest_index]} - Index: {indices[closest_index]}")

    # Return the original index of the closest level
    results[thread_id] = indices[closest_index]

def get_BeepLevel_Group3(distances, levels, thread_id):
    # Validate Inputs
    try:
        distances, levels, indices = verify_inputs(distances, levels)
        
    except ValueError as e:
        logging.error(f"[FUNC{thread_id}] {e}")
        results[thread_id] = -1
        return

    min_distance = min(distances)

    for i in range(len(levels)):
        if min_distance <= levels[i]:
            results[thread_id] = indices[i] - 1 if indices[i] > 0 else 0
            logging.debug(f"[FUNC{thread_id}] Chosen level: {levels[results[thread_id]]} - Index: {results[thread_id]}")
            return

    results[thread_id] = len(indices) - 1
    logging.debug(f"[FUNC{thread_id}] Chosen level: {levels[len(levels) - 1]} - Index: {results[thread_id]}")
    return

def get_BeepLevel_Group13(distances, levels, thread_id): 
     # Validate inputs
    try:
        distances, levels, indices = verify_inputs(distances, levels)
        
    except ValueError as e:
        logging.error(f"[FUNC{thread_id}] {e}")
        results[thread_id] = -1
        return
    
    if distances:
        min_distance = min(distances)  # vai buscar o objeto mais perto
    
    if not distances:
        return -1
    
    # procura o nivel correspondente ao objeto mais perto
    for i, level in reversed(list(enumerate(levels))):
        if level <= min_distance:
            logging.debug(f"[FUNC{thread_id}] Chosen level: {levels[i]} - Index: {indices[i]}")
            results[thread_id] = indices[i]
            return  
    
    logging.debug(f"[FUNC{thread_id}] Chosen level: {levels[0]} - Index: {0}")
    results[thread_id] = 0  # se nao tiver nenhum nivel correspondente ao objeto mais perto, retorna o beep level mais baixo

# N-Version Programming Approach
def SoftwareVoter(distances, levels):
    try:
        # Criar e iniciar as threads
        thread1 = threading.Thread(target=get_BeepLevel, args=(distances, levels, 0))
        thread2 = threading.Thread(target=get_BeepLevel_Group3, args=(distances, levels, 1))
        thread3 = threading.Thread(target=get_BeepLevel_Group13, args=(distances, levels, 2))

        thread1.start()
        thread2.start()
        thread3.start()

        # Wait for the threads to finish
        thread1.join(timeout=1)
        thread2.join(timeout=1)
        thread3.join(timeout=1)

        # Check if threads are still alive after the timeout
        if thread1.is_alive():
            logging.error("[VOTER] Thread 1 exceeded time limit.")
            results[0] = -1  # Mark as failed
        if thread2.is_alive():
            logging.error("[VOTER] Thread 2 exceeded time limit.")
            results[1] = -1  # Mark as failed
        if thread3.is_alive():
            logging.error("[VOTER] Thread 3 exceeded time limit.")
            results[2] = -1  # Mark as failed

        # Get the results from the threads        
        logging.debug(f"[VOTER] Results: FUNC0 {results[0]}, FUNC1 {results[1]}, FUNC2 {results[2]}")

        # Check for majority vote
        count = Counter(results)
        most_common = count.most_common(1)
        
        if most_common[0][1] >= 2:
            logging.debug(f"[VOTER] Most common result is {most_common[0][0]} with {most_common[0][1]} votes.")
            return most_common[0][0]  # Return the most common result
        else:
            logging.warning("[VOTER] Results differ between threads.")
            return -1  # No majority vote
        
    except Exception as e:
        logging.error(f"{e}")


# Test cases
test_cases = [
    ([], [], -1),  #                                     TC1
    ([100, 100], [5, 10, 20, 40, 70], 4),  #             TC2
    ([30, 100], [5, 10, 20, 40, 70], 2),  #              TC3
    ([1, 7, 13, 25, 45, 80], [5, 10, 20, 40, 70], 0),  # TC4
]


if __name__ == "__main__":
    for i, (distances, levels, expected) in enumerate(test_cases, 1):
        logging.info(f"TC{i} {distances} {levels}")     

        voter_result = SoftwareVoter(distances, levels)
        
        # Comparar o resultado com o esperado
        logging.info(f"TC{i}: Esperado={expected}, Obtido={voter_result}")
        print(f"TC{i}: {'✔' if voter_result == expected else '❌'}\tEsperado={expected}\tObtido={voter_result}")

