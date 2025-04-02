puts "Enter coordinates of point 1 (comma separated):"
point1 = gets.chomp # Get input from user, remove newline character

puts "Enter coordinates of point 2 (comma separated):"
point2 = gets.chomp # Get input from user, remove newline character

# Split the input strings into arrays of floats
coordinates1 = point1.split(",").map(&:to_f) 
coordinates2 = point2.split(",").map(&:to_f)

# Ensure both points have the same dimensionality
if coordinates1.size != coordinates2.size
  raise ArgumentError, "Points must have the same number of dimensions"
end

# Calculate the squared difference for each dimension
squared_differences = coordinates1.zip(coordinates2).map { |x, y| (x - y) ** 2 }

# Sum the squared differences and take the square root
distance = Math.sqrt(squared_differences.sum)

# Display the distance
puts "Euclidean distance between points: #{distance}"