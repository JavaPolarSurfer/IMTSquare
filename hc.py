import sys
import requests
import time

from bs4 import BeautifulSoup
import datetime
import re

r = requests.get(sys.argv[1])
soup = BeautifulSoup(r.text, features="html.parser")
price = soup.find('label', class_='price')
price_str = price.text
price_clean = re.search("\d+", price_str).group(0).replace(",",".")

print(price_clean)
