import sys
import requests
import time
from pprint import pprint
from bs4 import BeautifulSoup
import datetime
import re

r = requests.get(sys.argv[1])
soup = BeautifulSoup(r.text, features="html.parser")
price = soup.find('span', class_='prc-org')
price_str = price.text
price_clean = re.search("[+-]?([0-9]*[,])?[0-9]+", price_str).group(0).replace(",",".")

print(price_clean)

