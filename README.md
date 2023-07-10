# Exchange Verifier (Lite)

Exchange Verifier is a Kotlin project designed to verify if an exchange is functioning as expected by comparing its output with the an exchange's output. It provides a way to automate the verification process and detect any inconsistencies or issues in the exchange's behavior.

## Features

- Parses and validates input files containing orders for the exchange.
- Generate an order-book of buy and sell orders.
- Preform aggressive matching against buy and sell orders to detect possible trades.
- Print out trades and remaining order-book.



## Prerequisites

To run the Exchange Verifier, you need to have the following prerequisites installed on your system:

- Java Development Kit (JDK) 8 or higher
- Gradle build tool

## Getting Started

Follow these steps to set up and run the Exchange Verifier:

1. Clone the GitHub repository:

```shell
git clone https://github.com/Mo33n/my-exchange-verifier.git
```

2. Navigate to the project directory:

```shell   
cd my-exchange-verifier
```

3. Build the project using Gradle:

```shell
gradle build
```

4. Run the Exchange Verifier:

```shell
./exchange.sh < examples/test1.txt
```

Note : Sample dataset of orders are added in examples for reference only.



## Input File Format
The Exchange Verifier expects the input file to be in the following format:

```shell
10000,B,98,25500
10005,S,105,20000
10001,S,100,500
10002,S,100,10000
10003,B,99,50000
10004,S,103,100
10006,B,105,16000
...
```

Each line represents an order, with comma-separated values for order ID, side (B for BUY, S for SELL), price, and quantity.

## Output
The Exchange Verifier should generate same output which is expected from a real exchange.

Here is an example or output for above proided orders.
```
trade 10006,10001,100,500
trade 10006,10002,100,10000
trade 10006,10004,103,100
trade 10006,10005,105,5400
50,000      99     | 105    14,600     
25,500      98     |               
```

## Contributing
Contributions to the Exchange Verifier project are welcome! If you encounter any issues, have suggestions for improvements, or would like to contribute new features, feel free to open an issue or submit a pull request.

When contributing, please follow the existing code style and guidelines. Include tests for any new functionality or bug fixes to ensure the stability and reliability of the project.

## License
This project is licensed under the MIT License.

## Contact
For any questions or inquiries, please contact repo owner.


Feel free to customize the README.md file further based on your project's specific details, such as adding installation instructions, project structure, or additional sections as needed.