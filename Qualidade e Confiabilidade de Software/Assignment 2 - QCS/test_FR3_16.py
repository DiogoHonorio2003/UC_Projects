import pytest
from FR3_16 import tyrePressureWarnings

# -------- Black-Box Testing --------

# --- VALID INPUTS ---

# V1 - Todos os Inputs iguais ao target
def test_BV1():
    pressure = [[35, 35], [35, 35], [35, 35], [35, 35]]
    target = 35
    assert tyrePressureWarnings(pressure, target) == []

# V2 - Todos os Inputs inferiores ao target
def test_BV2():
    pressure = [[30, 30], [30, 30], [30, 30], [30, 30]]
    target = 35
    assert tyrePressureWarnings(pressure, target) == [0, 1, 2, 3]

# V3 - Todos os Inputs superiores ao target
def test_BV3():
    pressure = [[40, 40], [40, 40], [40, 40], [40, 40]]
    target = 35
    assert tyrePressureWarnings(pressure, target) == []

# V4 - Index 0 inferior ao target
def test_BV4():
    pressure = [[30, 30], [40, 40], [40, 40], [40, 40]]
    target = 35
    assert tyrePressureWarnings(pressure, target) == [0]

# V5 - Index 1 inferior ao target
def test_BV5():
    pressure = [[40, 40], [30, 30], [40, 40], [40, 40]]
    target = 35
    assert tyrePressureWarnings(pressure, target) == [1]

# V6 - Index 2 inferior ao target
def test_BV6():
    pressure = [[40, 40], [40, 40], [30, 30], [40, 40]]
    target = 35
    assert tyrePressureWarnings(pressure, target) == [2]

# V7 - Index 3 inferior ao target
def test_BV7():
    pressure = [[40, 40], [40, 40], [40, 40], [30, 30]]
    target = 35
    assert tyrePressureWarnings(pressure, target) == [3]

# V8 - Valores do input todos diferentes
def test_BV8():
    pressure = [[38, 36], [33, 41], [37, 36], [39, 34]]
    target = 35
    assert tyrePressureWarnings(pressure, target) == []

# V9 - Valores de virgula flutuante nas pressões
def test_BV9():
    pressure = [[34.2, 34.2], [40, 40], [40, 40], [40, 40]]
    target = 35
    assert tyrePressureWarnings(pressure, target) == [0]

# V10 - Valores de virgula flutuante no target
def test_BV10():
    pressure = [[33, 33], [38, 38], [38, 38], [38, 38]]
    target = 34.2
    assert tyrePressureWarnings(pressure, target) == [0]

# V11 - Ordenação dos valores das pressões
def test_BV11():
    pressure = [[38, 38], [32, 32], [31, 31], [33, 33]]
    target = 35
    assert tyrePressureWarnings(pressure, target) == [2, 1, 3]

# V12 - Varias pressões medidas diferentes
def test_BV12():
    pressure = [[31], [38, 38, 38], [33, 33, 33], [32, 32]]
    target = 35
    assert tyrePressureWarnings(pressure, target) == [0, 3, 2]

# V13 - Valores 0 em algumas pressões
def test_BV13():
    pressure = [[31, 31], [0, 38], [38, 38], [33, 33]]
    target = 35
    assert tyrePressureWarnings(pressure, target) == [0, 3]



    
# --- INVALID INPUTS ---

# I1 - Menos que quatro sublistas
def test_BI1():
    pressure = [[36, 36], [36, 36], [33, 33]]
    target = 35
    with pytest.raises(ValueError):
        tyrePressureWarnings(pressure, target)

# I2 - Mais que quatro sublistas
def test_BI2():
    pressure = [[36, 36], [36, 36], [36, 36], [34, 34], [32, 32]]
    target = 35
    with pytest.raises(ValueError):
        tyrePressureWarnings(pressure, target)

# I3 - Valor negativo de pressões
def test_BI3():
    pressure = [[-33, 36], [36, 36], [36, 36], [34, 34]]
    target = 35
    with pytest.raises(ValueError):
        tyrePressureWarnings(pressure, target)

# I4 - Valor negativo do target
def test_BI4():
    pressure = [[36, 36], [36, 36], [36, 36], [34, 34]]
    target = -35
    with pytest.raises(ValueError):
        tyrePressureWarnings(pressure, target)

# I5 - Leitura vazia numa das sublistas
def test_BI5():
    pressure = [[], [36, 36], [36, 36], [34, 34]]
    target = 35
    with pytest.raises(ValueError):
        tyrePressureWarnings(pressure, target)

# I6 - Apenas lista e não lista de listas
def test_BI6():
    pressure = [34, 34]
    target = 35
    with pytest.raises(ValueError):
        tyrePressureWarnings(pressure, target)

# I7 - Classes diferentes
def test_BI7():
    pressure = [[None, 36], [36, 36], [36, 36], [34, 34]]
    target = 35
    with pytest.raises(ValueError):
        tyrePressureWarnings(pressure, target)

# I8 - Valores acima dos limites nas pressures
def test_BI8():
    pressure = [[110, 110], [120, 120], [200, 220], [150, 155]]
    target = 35
    with pytest.raises(ValueError):
        tyrePressureWarnings(pressure, target)
        
# I9 - Valores acima dos limites no target
def test_BI9():
    pressure = [[32, 32], [45, 45], [58, 58], [34, 34]]
    target = 150
    with pytest.raises(ValueError):
        tyrePressureWarnings(pressure, target)
        
# I10 - Valores 0 em todas pressões
def test_BI10():
    pressure = [[0, 0], [0, 0], [0, 0], [0, 0]]
    target = 35
    with pytest.raises(ValueError):
        tyrePressureWarnings(pressure, target)

# -------- BVA --------

def test_BV2b_1():
    pressure = [[34, 34], [34, 34], [34, 34], [34, 34]]
    target = 35
    assert tyrePressureWarnings(pressure, target) == [0, 1, 2, 3]
    
def test_BV2b_2():
    pressure = [[1, 1], [1, 1], [1, 1], [1, 1]]
    target = 35
    assert tyrePressureWarnings(pressure, target) == [0, 1, 2, 3]
    
def test_BV3b_1():
    pressure = [[36, 36], [36, 36], [36, 36], [36, 36]]
    target = 35
    assert tyrePressureWarnings(pressure, target) == []

def test_BV3b_2():
    pressure = [[99, 99], [99, 99], [99, 99], [99, 99]]
    target = 35
    assert tyrePressureWarnings(pressure, target) == []

def test_BV4b_1():
    pressure = [[34, 34], [36, 36], [36, 36], [36, 36]]
    target = 35
    assert tyrePressureWarnings(pressure, target) == [0]
    
def test_BV4b_2():
    pressure = [[1, 1], [99, 99], [99, 99], [99, 99]]
    target = 35
    assert tyrePressureWarnings(pressure, target) == [0]

def test_BV5b_1():
    pressure = [[36, 36], [34, 34], [36, 36], [36, 36]]
    target = 35
    assert tyrePressureWarnings(pressure, target) == [1]
    
def test_BV5b_2():
    pressure = [[99, 99], [1, 1], [99, 99], [99, 99]]
    target = 35
    assert tyrePressureWarnings(pressure, target) == [1]

def test_BV6b_1():
    pressure = [[36, 36], [36, 36], [34, 34], [36, 36]]
    target = 35
    assert tyrePressureWarnings(pressure, target) == [2]
    
def test_BV6b_2():
    pressure = [[99, 99], [99, 99], [1, 1], [99, 99]]
    target = 35
    assert tyrePressureWarnings(pressure, target) == [2]

def test_BV7b_1():
    pressure = [[36, 36], [36, 36], [36, 36], [34, 34]]
    target = 35
    assert tyrePressureWarnings(pressure, target) == [3]
    
def test_BV7b_2():
    pressure = [[99, 99], [99, 99], [99, 99], [1, 1]]
    target = 35
    assert tyrePressureWarnings(pressure, target) == [3]

def test_BV9b_1():
    pressure = [[34.9, 34.9], [35.1, 35.1], [35.1, 35.1], [35.1, 35.1]]
    target = 35
    assert tyrePressureWarnings(pressure, target) == [0]
    
def test_BV9b_2():
    pressure = [[0.1, 0.1], [99.9, 99.9], [99.9, 99.9], [99.9, 99.9]]
    target = 35
    assert tyrePressureWarnings(pressure, target) == [0]
    
def test_BV10b_1():
    pressure = [[34.1, 34.1], [34.3, 34.3], [34.3, 34.3], [34.3, 34.3]]
    target = 34.2
    assert tyrePressureWarnings(pressure, target) == [0]
    
def test_BV10b_2():
    pressure = [[0.1, 0.1], [99.9, 99.9], [99.9, 99.9], [99.9, 99.9]]
    target = 34.2
    assert tyrePressureWarnings(pressure, target) == [0]
    
def test_BI3b_1():
    pressure = [[-0.1, 36], [36, 36], [36, 36], [34, 34]]
    target = 35
    with pytest.raises(ValueError):
        tyrePressureWarnings(pressure, target)

def test_BI3b_2():
    pressure = [[-999, 36], [36, 36], [36, 36], [34, 34]]
    target = 35
    with pytest.raises(ValueError):
        tyrePressureWarnings(pressure, target)
        
def test_BI4b_1():
    pressure = [[36, 36], [36, 36], [36, 36], [34, 34]]
    target = -0.1
    with pytest.raises(ValueError):
        tyrePressureWarnings(pressure, target)
        
def test_BI4b_2():
    pressure = [[36, 36], [36, 36], [36, 36], [34, 34]]
    target = -999
    with pytest.raises(ValueError):
        tyrePressureWarnings(pressure, target)
        
def test_BI8b_1():
    pressure = [[100.1, 100.1], [100.1, 100.1], [100.1, 100.1], [100.1, 100.1]]
    target = 35
    with pytest.raises(ValueError):
        tyrePressureWarnings(pressure, target)        

def test_BI8b_2():
    pressure = [[999, 999], [999, 999], [999, 999], [999, 999]]
    target = 35
    with pytest.raises(ValueError):
        tyrePressureWarnings(pressure, target) 
        
# -------- White-Box Testing --------

# --- Control Flow ---

def test_WCP1():
    pressure = []
    target = 10
    with pytest.raises(ValueError):
        tyrePressureWarnings(pressure, target)
    
def test_WCP4b():
    pressure = [[15]]
    target = 10
    assert tyrePressureWarnings(pressure, target) == []
    
def test_WCP5b():
    pressure = [[]]
    target = 0
    with pytest.raises(ValueError):
        tyrePressureWarnings(pressure, target)
       
def test_WCP6b():
    pressure = [[5]]
    target = 10
    assert tyrePressureWarnings(pressure, target) == [0]
    
def test_WCP7b():
    pressure = [[20]]
    target = 10
    assert tyrePressureWarnings(pressure, target) == []
    
def test_WCP8b():
    pressure = [[0,5]]
    target = 10
    assert tyrePressureWarnings(pressure, target) == [0]
     
def test_WCP9():
    pressure = [[0]]
    target = 0
    with pytest.raises(ValueError):
        tyrePressureWarnings(pressure, target)
    
# --- Data Flow ---

def test_WDP1b():
    pressure = [[0]]
    target = 0
    with pytest.raises(ValueError):
        tyrePressureWarnings(pressure, target)
        
def test_WDP2():
    pressure = []
    target = 10
    with pytest.raises(ValueError):
        tyrePressureWarnings(pressure, target)
        
def test_WDP3b():
    pressure = [[10]]
    target = 20
    assert tyrePressureWarnings(pressure, target) == [0]
    
def test_WDP4b():
    pressure = [[]]
    target = 0
    with pytest.raises(ValueError):
        tyrePressureWarnings(pressure, target)
        
def test_WDP5b():
    pressure = [[20]]
    target = 10
    assert tyrePressureWarnings(pressure, target) == []
    
def test_WDP6b():
    pressure = [[]]
    target = 10
    with pytest.raises(ValueError):
        tyrePressureWarnings(pressure, target)
        
def test_WDP8b():
    pressure = [[0,10]]
    target = 20
    assert tyrePressureWarnings(pressure, target) == [0]