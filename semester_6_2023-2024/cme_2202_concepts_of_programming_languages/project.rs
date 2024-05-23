use std::f64;
use std::str::FromStr;

fn main() {
    let mut input = String::new();
    println!("Enter the first point (comma-separated coordinates): ");
    std::io::stdin().read_line(&mut input).expect("Failed to read input");
    let point1: Vec<f64> = input.trim().split(',')
        .map(|x| f64::from_str(x).expect("Invalid number format"))
        .collect();

    let mut input = String::new();
    println!("Enter the second point (comma-separated coordinates): ");
    std::io::stdin().read_line(&mut input).expect("Failed to read input");
    let point2: Vec<f64> = input.trim().split(',')
        .map(|x| f64::from_str(x).expect("Invalid number format"))
        .collect();

    if point1.len() != point2.len() {
        println!("Points must have the same number of dimensions.");
        return;
    }

    let distance = point1.iter()
        .zip(point2.iter())
        .map(|(&d1, &d2)| (d1 - d2) * (d1 - d2))
        .sum::<f64>()
        .sqrt();

    println!("Euclidean distance: {}", distance);
}