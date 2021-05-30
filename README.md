# Keenan's POS

### Building the project

This project requires maven 3.6.0 and java 1.8 to build.

It can be built by running `mvn clean install`

### Running the project

After being built there will be a .jar created in the target folder called `keenan-pos.jar`

To run the jar, use the following command
`java -jar keenan-pos.jar <path to data csv file>`

### About

The following commands are available while running the software:

1. `q` to quit the program
2. Any number less than twelve digits will return all barcodes that match the given prefix
3. A 12 digit number will add the product to the current total.
4. When at least one product has been added, you can run `checkout` to create a receipt

### Assumptions made

1. The code will not be run in a multi-threaded fashion
2. The user will not input negative numbers for the amount paid
3. The given data file is well formatted and contains all valid values
