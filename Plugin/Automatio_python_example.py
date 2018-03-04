# HTTP Client for Python
import requests
import unittest

# Standard JSON library
import json
from requests.status_codes import codes



# Basic Setup
PORT_NUMBER = 1234
HOST = 'localhost'
#BASE = 'http://localhost:' + str(PORT_NUMBER) + '/v1/'

# Header for posting data to the server as JSON
HEADERS = {'Content-Type': 'application/json'}


def function_example():
    """
    Returns the Number of Occurrences. as a dictionary where the key is the motif binary representation
                     and the value is an array that has two numbers, firstthe number of occurences and
                      the second is the z-score
    """

    # Set up HTTP Request headers for our request.
    headers = {'Content-type': 'application/json', 'Accept': 'application/json'}

    #Set the parameters
    motif_size = 3
    directed = True  # False
    num_random_networks = 10  # zero if you don't want to calculate the z-score

    # Perform the request
    result = requests.request("GET",
                              "http://" + HOST + ":" + str(
                                  PORT_NUMBER) + "/cydiscovermotifs/FindOccurrences/{}/{}/{}".format(
                                  motif_size, directed, num_random_networks),
                              data=None,
                              params=None,
                              headers=headers)

    assert result.status_code == codes.OK, "Status code was expected to be 200, but was {}".format(result.status_code)

    #Here, we extract fields from the JSON content of the result.

    dict_of_occurrences = result.json()  # ["message"]
    print(type(dict_of_occurrences))

    print(dict_of_occurrences)
    for motif in dict_of_occurrences.keys():
        print("Motif: {}, Number of Occurrences: {}, z-Score: {}".format(motif,dict_of_occurrences[motif][0], dict_of_occurrences[motif][1]))


def command_example():
    """
    Returns the Number of Occurrences. as a dictionary where the key is the motif binary representation
                     and the value is an array that has two numbers, firstthe number of occurences and
                      the second is the z-score
    """
    # Set up HTTP Request headers for our request.
    headers = {'Content-type': 'text/plain', 'Accept': 'text/plain'}
    # Set the parameters
    motif_size = 3
    directed = True  # False#
    num_random_networks = 10  # 10

    # Perform the request. Note that our path includes path arguments
    result = requests.request("GET",
                                  "http://" + HOST + ":" + str(
                                   PORT_NUMBER) + "/v1/commands/cydiscovermotifs/FindOccurrences?motifSize={}&directed={}&numRandomNetworks={}".format(
                                   motif_size, directed, num_random_networks),
                                   data=None,
                                   params=None,
                                   headers=headers)

    assert result.status_code == codes.OK, "Status code was expected to be 200, but was {}".format(
            result.status_code)


    message = result.text
    dict_of_occurrences = json.loads(message.split('\n')[0])

    print(type(dict_of_occurrences))

    print(dict_of_occurrences)
    for motif in dict_of_occurrences.keys():
            print("Motif: {}, Number of Occurrences: {}, z-Score: {}".format(motif, dict_of_occurrences[motif][0],
                                                                             dict_of_occurrences[motif][1]))



# This defines our main method for execution.
if __name__ == "__main__":
    print("Run the function way")
    function_example()
    print("------------------------")
    print("Run the command function")
    command_example()

