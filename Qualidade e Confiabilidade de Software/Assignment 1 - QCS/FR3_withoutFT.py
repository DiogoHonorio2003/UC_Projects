import time

inicio = time.time()

def tyre_pressure_warning(pressures, target_pressure):
    tyre_averages = []
    
    for readings in pressures:
        valid_readings = [p for p in readings if p > 0]  
        avg_pressure = sum(valid_readings) / len(valid_readings)
        tyre_averages.append(avg_pressure)
    
    warning_tyres = [i for i, avg in enumerate(tyre_averages) if avg < target_pressure]
    
    return sorted(warning_tyres, key=lambda i: tyre_averages[i])

# Test cases
print(tyre_pressure_warning([[10], [10], [10], [10]], 5))  # []
print(tyre_pressure_warning([[10, 10], [10, 10], [10, 10], [10, 10]], 5))  # []
print(tyre_pressure_warning([[10, 10], [30, 30], [20, 20], [40, 40]], 100))  # [0, 2, 1, 3]
print(tyre_pressure_warning([[10], [1], [10], [10]], 5))  # [1]
print(tyre_pressure_warning([[0, 15], [10, 10], [10, 10], [10, 10]], 8))  # []

fim = time.time() 
print(f"Tempo de execução: {fim - inicio:.5f} segundos")