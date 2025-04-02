package main

import (
  "fmt"
  "math"
  "strconv"
  "strings"
)

func main() {
  var point1Str, point2Str string

  fmt.Println("Enter coordinates for first point (comma-separated): ")
  fmt.Scanln(&point1Str)  // Read entire line with Scanln

  fmt.Println("Enter coordinates for second point (comma-separated): ")
  fmt.Scanln(&point2Str)

  // Split strings and convert to float64 (inline)
  point1StrArr := strings.Split(point1Str, ",")
  point2StrArr := strings.Split(point2Str, ",")
  point1 := make([]float64, len(point1StrArr))
  point2 := make([]float64, len(point2StrArr))

  for i, val := range point1StrArr {
    val = strings.TrimSpace(val) // Remove leading/trailing whitespaces
    f, err := strconv.ParseFloat(val, 64)
    if err != nil {
      fmt.Println("Error parsing point1:", err)
      return
    }
    point1[i] = f
  }
  
  for i, val := range point2StrArr {
    val = strings.TrimSpace(val) // Remove leading/trailing whitespaces
    f, err := strconv.ParseFloat(val, 64)
    if err != nil {
      fmt.Println("Error parsing point2:", err)
      return
    }
    point2[i] = f
  }

  // Ensure both points have the same dimensionality
  if len(point1) != len(point2) {
    fmt.Println("Points must have the same number of dimensions.")
    return
  }

  var sumSqDiff float64
  // Calculate squared difference for each dimension (inline)
  for i := range point1 {
    diff := point2[i] - point1[i]
    sumSqDiff += math.Pow(diff, 2)
  }

  // Calculate and display distance (inline)
  distance := math.Sqrt(sumSqDiff)
  fmt.Printf("Euclidean distance between points: %.2f\n", distance)
}
