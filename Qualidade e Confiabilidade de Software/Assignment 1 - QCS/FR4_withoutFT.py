
def get_BeepLevel(distances,levels):
    if len(distances) < 2:
        return -1

    min_distance = min(distances)

    # Find the closest beep level
    closest_index = 0
    closest_diff = abs(min_distance - levels[0])
    for i, level in enumerate(levels):
        diff = abs(min_distance - level)
        if diff < closest_diff and level <= min_distance:
            closest_diff = diff
            closest_index = i

    return closest_index

# Test cases
test_cases = [
    ([], [], -1),  #                                     TC1   No distances or levels
    ([100, 100], [5, 10, 20, 40, 70], 4),  #             TC2   Valid case
    ([30, 100], [5, 10, 20, 40, 70], 2),  #              TC3   Valid case
    ([1, 7, 13, 25, 45, 80], [5, 10, 20, 40, 70], 0),  # TC4   Valid case
]

if __name__ == "__main__":
    for i, (distances, levels, expected) in enumerate(test_cases, 1):
        result = get_BeepLevel(distances, levels)
        print(f"TC{i}: Esperado={expected}, Obtido={result}, {'✔' if result == expected else '❌'}")

        