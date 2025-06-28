import pytest
from FR2_1 import cruise_control


# ----------------------------------- BB VALID CASES -----------------------------------

# 1.	Teste de velocidade atual é igual à velocidade pretendida
def test_current_speed_same_as_set_speed():
    result = cruise_control(100, [100,100], 100, 10, 10)
    assert result == 0.0, f"Expected 0.0, but got {result}"

# 2.	Teste de velocidade atual abaixo à velocidade pretendida sem erro
def test_below_range_without_error():
    result = cruise_control(45, [100,100], 50, 5, 0)
    assert result == 5.0, f"Expected 5.0, but got {result}"

# 3.	Teste de velocidade atual acima à velocidade pretendida sem erro
def test_above_range_without_error():
    result = cruise_control(55, [100,100], 50, 5, 0)
    assert result == -5.0, f"Expected -5.0, but got {result}"

# 4.	Teste de velocidade atual abaixo à velocidade pretendida com erro
def test_below_range_with_error():
    result = cruise_control(90, [100, 110], 100, 10, 5)
    assert result == 5.0, f"Expected 5.0, but got {result}"

# 5.	Teste de velocidade atual acima à velocidade pretendida com erro
def test_above_range_with_error():
    result = cruise_control(110, [100, 90], 100, 10, 5)
    assert result == -5.0, f"Expected -5.0, but got {result}"

# 6.	Teste de aceleração máxima igual a zero
def test_zero_max_accel():
    result = cruise_control(90, [100, 110], 100, 0, 5)
    assert result == 0.0, f"Expected 0.0, but got {result}"

# 7.	Teste de velocidade pretendida fora de alcance
def test_target_speed_out_of_range():
    result = cruise_control(50, [50, 50], 200, 10, 5)
    assert result == 10.0, f"Expected 10.0, but got {result}"   

# ----------------------------------- BB INVALID CASES -----------------------------------

# 1.	Teste de valores de entrada não numéricos
def test_non_numeric_values():
    result = cruise_control("90", ["100", "110"], "100", "5", "5")
    assert result == 5.0, f"Expected 0.0, but got {result}"

# 2.	Teste de histórico de velocidades vazio
def test_empty_history():
    with pytest.raises(Exception):
        cruise_control(90, [], 100, 10, 5)

# 3.	Teste de velocidade atual com valores negativos
def test_negative_current_speed():
    with pytest.raises(Exception):
        cruise_control(-50, [0, 0], 100, 10, 5)

# 4.	Teste de aceleração máxima com valores negativos
def test_negative_max_accel():
    with pytest.raises(Exception):
        cruise_control(45, [40,45], 50, -5, 2)

# 5.	Teste de erro com valores negativos
def test_negative_error():
    with pytest.raises(Exception):
        cruise_control(45, [40,45], 50, 5, -2)              

# 6.	Teste de valores de entrada nulos
def test_none_values():
    with pytest.raises(Exception):
        cruise_control(None, [], None, None, None)

# 7.	Teste de set_speed com valores negativos
def test_negative_set_speed():
    with pytest.raises(Exception):
        cruise_control(45, [40,45], -50, 5, 2)  

# ----------------------------------- BVA CASES -----------------------------------

# 2.	Teste de velocidade atual abaixo à velocidade pretendida sem erro
def test_below_range_without_error_bva_closest():
    result = cruise_control(49.9, [100,100], 50, 1, 0)
    assert result == 0.1, f"Expected 0.1, but got {result}"

def test_below_range_without_error_bva_farthest():
    result = cruise_control(0.1, [100,100], 50, 49, 0)
    assert result == 49, f"Expected 49, but got {result}"


# 3.	Teste de velocidade atual acima à velocidade pretendida sem erro
def test_above_range_without_error_bva_closest():
    result = cruise_control(50.1, [100,100], 50, 1, 0)
    assert result == -0.1, f"Expected -0.1, but got {result}"

def test_above_range_without_error_bva_farthest():
    result = cruise_control(199.9, [100,100], 50, 50, 0)
    assert result == -50.0, f"Expected -50.0, but got {result}"


# 4.	Teste de velocidade atual abaixo à velocidade pretendida com erro
def test_below_range_with_error_bva_closest():
    result = cruise_control(49.9, [100,100], 50, 1, 0.01)
    assert result == 0.09, f"Expected 0.09, but got {result}"

def test_below_range_with_error_bva_farthest():
    result = cruise_control(0.1, [100,100], 50, 49, 49.8)
    assert result == 0.1, f"Expected 0.1, but got {result}"


# 5.	Teste de velocidade atual acima à velocidade pretendida com erro
def test_above_range_with_error_bva_closest():
    result = cruise_control(50.1, [100,100], 50, 1, 0.01)
    assert result == -0.09, f"Expected -0.09, but got {result}"

def test_above_range_with_error_bva_farthest():
    result = cruise_control(199.9, [100,100], 50, 50, 149.8)
    assert result == -0.1, f"Expected -0.09, but got {result}"



# ----------------------------------- WB VALID CASES -----------------------------------
#   CFG                             DFG

# S, 1, 2, E                    1,2,3
def test_wb_1():
    with pytest.raises(Exception):
        cruise_control(100, [], 100, -1, 1)

# S, 1, 3, 2, E                 1,2,4,3
def test_wb_2():
    with pytest.raises(Exception):
        cruise_control(100, [], 100, 1, -1)

# S, 1, 3, 4, 5, E              1,2,4,5,6,7
def test_wb_3():
    result = cruise_control(100, [], 100, 10, 0)
    assert result == 0.0, f"Expected 0.0, but got {result}"   

# S, 1, 3, 4, 6, 7, E           1,2,4,5,6,8,9
def test_wb_4():
    result = cruise_control(80, [], 100, 10, 15)
    assert result == 5.0, f"Expected 5.0, but got {result}"   

# S, 1, 3, 4, 6, 8, 9, E        1,2,4,5,6,8,10,11
def test_wb_5():
    result = cruise_control(100, [], 80, 10, 10)
    assert result == -10.0, f"Expected -10.0, but got {result}"   

# S, 1, 3, 4, 6, 8, 10, E       1,2,4,5,6,8,10,12
def test_wb_5():
    result = cruise_control(100, [], 120, 10, 10)
    assert result == 10.0, f"Expected 10.0, but got {result}"   
