
# Berkeley Algorithm UDP

This project contains a Berkeley algorithm that can be used in an internal network to synchronize machine clocks.

## Usage Instructions

### Prerequisites

Make sure to have Java JDK installed on all machines that will be part of the network.

### Configuration

1.  Clone this repository on all machines that will be part of the network.
    
2.  On each machine, navigate to the `berkeley` directory and run the following command in the terminal:
    

`java ServerBerkeley` 

This will start the Berkeley server that will be used to synchronize clocks.

3.  On one of the machines, choose a master machine and navigate to the `berkeley` directory. Then, run the following command:

`java berkeley.BerkeleyCalculator` 

This will start the Berkeley algorithm and synchronize the clocks of all machines.

### Simplified Usage

If preferred, you can simply run the `run_berkeley.sh` script on the master machine. This script automatically runs the necessary commands to start the Berkeley server and the synchronization algorithm.

Note: running the script requires super user permissions.
