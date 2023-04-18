import sys
import requests
import time
from pprint import pprint
from bs4 import BeautifulSoup
import datetime


print("Number of arguments:", len(sys.argv), "arguments.")
print("Argument List:", str(sys.argv))

r = requests.get(sys.argv[1])
soup = BeautifulSoup(r.text, features="html.parser")
price = soup.find('span', class_='prc-org')
price_str = price.text

print(price_str)

