#!/bin/bash

# Run the ServerBerkeley program on ens2 and ens3
ssh user@ens2 "cd berkeley && javac ServerBerkeley.java && java ServerBerkeley" &
ssh user@ens3 "cd berkeley && javac ServerBerkeley.java && java ServerBerkeley" &

# Run the BerkeleyCalculator program on ens1
ssh user@ens1 "cd berkeley && javac berkeley/BerkeleyCalculator.java && java berkeley.BerkeleyCalculator"
