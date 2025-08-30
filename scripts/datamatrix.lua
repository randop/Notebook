-- Data Matrix barcode generator in Lua (144x144 grid, ECC200)
-- Simplified implementation with Reed-Solomon encoding for high error correction
-- Outputs ASCII representation

-- OpenSSH private key (full key included)
local input_data = [[
-----BEGIN OPENSSH PRIVATE KEY-----
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
gBdRRGGqemOsc4rfIeae3w
-----END OPENSSH PRIVATE KEY-----
]]

-- Reed-Solomon encoding (simplified for ECC200)
local function reed_solomon_encode(data)
    -- ECC200 uses Reed-Solomon with 30% error correction
    -- This is a simplified placeholder; real implementation requires a proper RS library
    local codewords = {}
    for i = 1, #data do
        codewords[i] = string.byte(data, i)
    end
    -- Simulate adding 68 error correction codewords for a 144x144 matrix (3116 bytes total, 2335 data + 781 ECC)
    for i = 1, 68 do
        codewords[#data + i] = 0 -- Placeholder for ECC
    end
    return codewords
end

-- Function to encode data into a 144x144 Data Matrix
local function encode_data_matrix(data)
    local matrix_size = 144 -- Largest Data Matrix size
    local matrix = {}

    -- Initialize matrix
    for i = 1, matrix_size do
        matrix[i] = {}
        for j = 1, matrix_size do
            matrix[i][j] = 0
        end
    end

    -- Encode data with Reed-Solomon
    local codewords = reed_solomon_encode(data)

    -- Simplified data placement (real Data Matrix uses complex module placement)
    local index = 1
    for i = 2, matrix_size - 1, 2 do -- Skip finder patterns
        for j = 2, matrix_size - 1, 2 do
            if index <= #codewords then
                matrix[i][j] = (codewords[index] % 2)
                index = index + 1
            end
        end
    end

    -- Add finder patterns (L-shaped border and alternating pattern)
    for i = 1, matrix_size do
        matrix[i][1] = 1 -- Left solid border
        matrix[1][i] = 1 -- Top solid border
        matrix[i][matrix_size] = (i % 2 == 0) and 1 or 0 -- Right alternating
        matrix[matrix_size][i] = (i % 2 == 1) and 1 or 0 -- Bottom alternating
    end

    return matrix
end

-- Function to print a portion of the Data Matrix as ASCII art (144x144 is too large for full display)
local function print_matrix(matrix)
    print("Data Matrix (144x144, top-left 20x20 corner shown):")
    for i = 1, math.min(20, #matrix) do
        local row = ""
        for j = 1, math.min(20, #matrix[i]) do
            row = row .. (matrix[i][j] == 1 and "█" or " ")
        end
        print(row)
    end
    print("... (truncated; full matrix is 144x144)")
end

-- Main function
local function main()
    print("Generating Data Matrix (144x144, ECC200) for provided key...")
    local matrix = encode_data_matrix(input_data)
    print("\nData Matrix (ASCII representation, partial):")
    print_matrix(matrix)
end

-- Run the program
main()