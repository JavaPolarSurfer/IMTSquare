import sys
import requests
import time

from bs4 import BeautifulSoup
import datetime
import re

r = requests.get(sys.argv[1])
soup = BeautifulSoup(r.text, features="html.parser")
price = soup.find('span', class_='prc-org')
price_str = price.text
price_clean = re.search("[+-]?([0-9]*[,])?[0-9]+", price_str).group(0).replace(",",".")

print(price_clean)

############## For Diapers ##########################

r_molfix = requests.get("https://www.trendyol.com/molfix/bebek-bezi-4-beden-hiper-ekonomi-paketi-p-31622474?")
soup_molfix = BeautifulSoup(r_molfix.text, features="html.parser")
price_molfix =soup_molfix.find("div", {"class": "pr-bx-nm with-org-prc"}).find("span", {"class": "prc-dsc"})
price_molfix_str = price_molfix.text
price_molfix_clean = re.search("[+-]?([0-9]*[,])?[0-9]+", price_molfix_str).group(0).replace(",",".")
print(price_molfix_clean)


r_sleepy = requests.get("https://www.trendyol.com/sleepy/natural-mega-paket-bebek-bezi-4-numara-maxi-152-adet-p-375767874")
soup_sleepy = BeautifulSoup(r_sleepy.text, features="html.parser")
price_sleepy =soup_sleepy.find("div", {"class": "pr-bx-nm with-org-prc"}).find("span", {"class": "prc-dsc"})
price_sleepy_str = price_sleepy.text
price_sleepy_clean = re.search("[+-]?([0-9]*[,])?[0-9]+", price_sleepy_str).group(0).replace(",",".")
print(price_sleepy_clean)

r_turco = requests.get("https://www.trendyol.com/baby-turco/dogadan-5-numara-junior-120-adet-p-49294635")
soup_turco = BeautifulSoup(r_turco.text, features="html.parser")
price_turco =soup_turco.find("div", {"class": "pr-bx-nm with-org-prc"}).find("span", {"class": "prc-dsc"})
price_turco_str = price_turco.text
price_turco_clean = re.search("[+-]?([0-9]*[,])?[0-9]+", price_turco_str).group(0).replace(",",".")
print(price_turco_clean)

r_prima = requests.get("https://www.trendyol.com/prima/bebek-bezi-premium-care-1-beden-70-adet-yenidogan-jumbo-paket-p-37162234")
soup_prima = BeautifulSoup(r_prima.text, features="html.parser")
price_prima =soup_prima.find("div", {"class": "pr-bx-nm with-org-prc"}).find("span", {"class": "prc-dsc"})
price_prima_str = price_prima.text
price_prima_clean = re.search("[+-]?([0-9]*[,])?[0-9]+", price_prima_str).group(0).replace(",",".")
print(price_prima_clean)

r_pure_baby = requests.get("https://www.trendyol.com/pure-baby/organik-pamuklu-cirtli-bez-ekonomik-paket-4-numara-maxi-100-adet-p-462253179")
soup_pure_baby = BeautifulSoup(r_pure_baby.text, features="html.parser")
price_pure_baby =soup_pure_baby.find("div", {"class": "pr-bx-nm with-org-prc"}).find("span", {"class": "prc-dsc"})
price_pure_baby_str = price_pure_baby.text
price_pure_baby_clean = re.search("[+-]?([0-9]*[,])?[0-9]+", price_pure_baby_str).group(0).replace(",",".")
print(price_pure_baby_clean)

############## Baby Formula ##########################

r_aptamil = requests.get("https://www.trendyol.com/aptamil/4-cocuk-devam-sutu-1-yas-250-gr-p-69050707?boutiqueId=61&merchantId=206847")
soup_aptamil = BeautifulSoup(r_aptamil.text, features="html.parser")
price_aptamil =soup_aptamil.find("div", {"class": "pr-bx-nm with-org-prc"}).find("span", {"class": "prc-dsc"})
price_aptamil_str = price_aptamil.text
price_aptamil_clean = re.search("[+-]?([0-9]*[,])?[0-9]+", price_aptamil_str).group(0).replace(",",".")
print(price_aptamil_clean)

r_herobaby = requests.get("https://www.trendyol.com/hero-baby/nutradefense-1-350-g-p-255916024?boutiqueId=61&merchantId=140846")
soup_herobaby = BeautifulSoup(r_herobaby.text, features="html.parser")
price_herobaby =soup_herobaby.find("div", {"class": "pr-bx-nm with-org-prc"}).find("span", {"class": "prc-dsc"})
price_herobaby_str = price_herobaby.text
price_herobaby_clean = re.search("[+-]?([0-9]*[,])?[0-9]+", price_herobaby_str).group(0).replace(",",".")
print(price_herobaby_clean)

r_bebelac = requests.get("https://www.trendyol.com/bebelac/gold-2-350-gr-p-6002447?boutiqueId=61&merchantId=120843")
soup_bebelac = BeautifulSoup(r_bebelac.text, features="html.parser")
price_bebelac =soup_bebelac.find("div", {"class": "pr-bx-nm with-org-prc"}).find("span", {"class": "prc-dsc"})
price_bebelac_str = price_bebelac.text
price_bebelac_clean = re.search("[+-]?([0-9]*[,])?[0-9]+", price_bebelac_str).group(0).replace(",",".")
print(price_bebelac_clean)

r_similac = requests.get("https://www.trendyol.com/similac/3-devam-sutu-360-gr-p-237100251?boutiqueId=61&merchantId=121673")
soup_similac = BeautifulSoup(r_similac.text, features="html.parser")
price_similac =soup_similac.find("div", {"class": "pr-bx-nm with-org-prc"}).find("span", {"class": "prc-dsc"})
price_similac_str = price_similac.text
price_similac_clean = re.search("[+-]?([0-9]*[,])?[0-9]+", price_similac_str).group(0).replace(",",".")
print(price_similac_clean)

r_bebevita = requests.get("https://www.trendyol.com/pd/hipp/bebivita-1-bebek-sutu-500-gr-1-paket-1-x-500-g-p-348659266?boutiqueId=61&merchantId=731617")
soup_bebevita = BeautifulSoup(r_bebevita.text, features="html.parser")
price_bebevita =soup_bebevita.find("div", {"class": "pr-bx-nm with-org-prc"}).find("span", {"class": "prc-dsc"})
price_bebevita_str = price_bebevita.text
price_bebevita_clean = re.search("[+-]?([0-9]*[,])?[0-9]+", price_bebevita_str).group(0).replace(",",".")
print(price_bebevita_clean)

r_sma = requests.get("https://www.trendyol.com/sma/optipro-probiyotik-2-devam-sutu-400-gr-p-133310901?boutiqueId=61&merchantId=408752")
soup_sma = BeautifulSoup(r_sma.text, features="html.parser")
price_sma =soup_sma.find("div", {"class": "pr-bx-nm with-org-prc"}).find("span", {"class": "prc-dsc"})
price_sma_str = price_sma.text
price_sma_clean = re.search("[+-]?([0-9]*[,])?[0-9]+", price_sma_str).group(0).replace(",",".")
print(price_sma_clean)

r_lactamil = requests.get("https://www.trendyol.com/milupa/lactamil-200gr-emziren-annelerin-icin-devam-sutu-p-44126999?boutiqueId=61&merchantId=383763")
soup_lactamil = BeautifulSoup(r_lactamil.text, features="html.parser")
price_lactamil =soup_lactamil.find("div", {"class": "pr-bx-nm with-org-prc"}).find("span", {"class": "prc-dsc"})
price_lactamil_str = price_lactamil.text
price_lactamil_clean = re.search("[+-]?([0-9]*[,])?[0-9]+", price_lactamil_str).group(0).replace(",",".")
print(price_lactamil_clean)

############## Baby Wet Wipes ##########################

r_uni_baby = requests.get("https://www.trendyol.com/uni-baby/aile-islak-mendil-12-li-1080-yaprak-p-167198281")
soup_uni_baby = BeautifulSoup(r_uni_baby.text, features="html.parser")
price_uni_baby =soup_uni_baby.find("div", {"class": "pr-bx-nm with-org-prc"}).find("span", {"class": "prc-dsc"})
price_uni_baby_str = price_uni_baby.text
price_uni_baby_clean = re.search("[+-]?([0-9]*[,])?[0-9]+", price_uni_baby_str).group(0).replace(",",".")
print(price_uni_baby_clean)

r_sleepy_wipe = requests.get("https://www.trendyol.com/sleepy/bio-natural-yenidogan-islak-bebek-bakim-havlusu-12x40-480-yaprak-p-246412936")
soup_sleepy_wipe = BeautifulSoup(r_sleepy_wipe.text, features="html.parser")
price_sleepy_wipe =soup_sleepy_wipe.find("div", {"class": "pr-bx-nm with-org-prc"}).find("span", {"class": "prc-dsc"})
price_sleepy_wipe_str = price_sleepy_wipe.text
price_sleepy_wipe_clean = re.search("[+-]?([0-9]*[,])?[0-9]+", price_sleepy_wipe_str).group(0).replace(",",".")
print(price_sleepy_wipe_clean)

r_babyturco_wipe = requests.get("https://www.trendyol.com/baby-turco/beyaz-sabun-kokulu-islak-havlu-24x90-p-348101381?advertItems=eyJhZHZlcnRJZCI6IjAwYTlkYzg1LWQxYTAtNDVjZi04ZjkzLWY2M2VjODZkMDkzMSIsInNvcnRpbmdTY29yZSI6MC4xNjM3Mjc1OTIyODA5OTI2NSwiYWRTY29yZSI6MC4wNTMxNTgzMDkxODIxNDA0NywiYWRTY29yZXMiOnsiMSI6MC4wNTMxNTgzMDkxODIxNDA0NywiMiI6MC4wMTgyMDkzMDgwNDU5NjgxNH0sImNwYyI6My4wOCwibWluQ3BjIjowLjAxLCJlQ3BjIjoyLjExNzY5NzA4MjYyOTQ4NCwiYWR2ZXJ0U2xvdCI6MSwib3JkZXIiOjIsImF0dHJpYnV0ZXMiOiJTdWdnZXN0aW9uX0EsUmVsZXZhbmN5XzEsRmlsdGVyUmVsZXZhbmN5XzEsTGlzdGluZ1Njb3JpbmdBbGdvcml0aG1JZF8xLFNtYXJ0bGlzdGluZ18yLFN1Z2dlc3Rpb25CYWRnZXNfQSxQcm9kdWN0R3JvdXBUb3BQZXJmb3JtZXJfQSxPcGVuRmlsdGVyVG9nZ2xlXzIsU3VnZ2VzdGlvblN0b3JlQWRzX0IsQmFkZ2VCb29zdF9BIn0=")
soup_babyturco_wipe = BeautifulSoup(r_babyturco_wipe.text, features="html.parser")
price_babyturco_wipe =soup_babyturco_wipe.find("div", {"class": "pr-bx-nm with-org-prc"}).find("span", {"class": "prc-dsc"})
price_babyturco_wipe_str = price_babyturco_wipe.text
price_babyturco_wipe_clean = re.search("[+-]?([0-9]*[,])?[0-9]+", price_babyturco_wipe_str).group(0).replace(",",".")
print(price_babyturco_wipe_clean)

r_molfix_wipe = requests.get("https://www.trendyol.com/molfix/izotonik-sulu-islak-mendil-ferah-60li-x-24-adet-1440-yaprak-p-78460073?advertItems=eyJhZHZlcnRJZCI6IjAwYTlkYzg1LWQxYTAtNDVjZi04ZjkzLWY2M2VjODZkMDkzMSIsInNvcnRpbmdTY29yZSI6MC4xMTI1NzMxOTYyNzI1MzQ5NywiYWRTY29yZSI6MC4wMzY1NDk3MzkwNDk1MjQzNCwiYWRTY29yZXMiOnsiMSI6MC4wMzY1NDk3MzkwNDk1MjQzNCwiMiI6MC4wMzEyMjU1NDcxMzEzNzcyNH0sImNwYyI6My4wOCwibWluQ3BjIjowLjAxLCJlQ3BjIjoyLjE0NjM5OTA5MDQzNzIwMzIsImFkdmVydFNsb3QiOjIsIm9yZGVyIjo1LCJhdHRyaWJ1dGVzIjoiU3VnZ2VzdGlvbl9BLFJlbGV2YW5jeV8xLEZpbHRlclJlbGV2YW5jeV8xLExpc3RpbmdTY29yaW5nQWxnb3JpdGhtSWRfMSxTbWFydGxpc3RpbmdfMixTdWdnZXN0aW9uQmFkZ2VzX0EsUHJvZHVjdEdyb3VwVG9wUGVyZm9ybWVyX0EsT3BlbkZpbHRlclRvZ2dsZV8yLFN1Z2dlc3Rpb25TdG9yZUFkc19CLEJhZGdlQm9vc3RfQSJ9")
soup_molfix_wipe = BeautifulSoup(r_molfix_wipe.text, features="html.parser")
price_molfix_wipe =soup_molfix_wipe.find("div", {"class": "pr-bx-nm with-org-prc"}).find("span", {"class": "prc-dsc"})
price_molfix_wipe_str = price_molfix_wipe.text
price_molfix_wipe_clean = re.search("[+-]?([0-9]*[,])?[0-9]+", price_molfix_wipe_str).group(0).replace(",",".")
print(price_molfix_wipe_clean)


r_wiiwipes = requests.get("https://www.trendyol.com/bii-wipes/comfort-100-lu-gul-kokulu-islak-havlu-100-x-12-adet-p-313730170?advertItems=eyJhZHZlcnRJZCI6IjAxMDhlYzJmLWIzYWMtNGI2MC05MWRhLWI0OTQyMTc5OTZkZiIsInNvcnRpbmdTY29yZSI6MC4wNDkyNzY5MTU5MjUyMjYxNCwiYWRTY29yZSI6MC4wNDkyNzY5MTU5MjUyMjYxNCwiYWRTY29yZXMiOnsiMSI6MC4wNDkyNzY5MTU5MjUyMjYxNCwiMiI6MC4wNDQyNDY3MjY1MTkyOTg3MzZ9LCJjcGMiOjEsIm1pbkNwYyI6MC4wMSwiZUNwYyI6MC44NDA3Nzk1OTU1MzUwMTE0LCJhZHZlcnRTbG90Ijo3LCJvcmRlciI6MTksImF0dHJpYnV0ZXMiOiJTdWdnZXN0aW9uX0EsUmVsZXZhbmN5XzEsRmlsdGVyUmVsZXZhbmN5XzEsTGlzdGluZ1Njb3JpbmdBbGdvcml0aG1JZF8xLFNtYXJ0bGlzdGluZ18yLFN1Z2dlc3Rpb25CYWRnZXNfQSxQcm9kdWN0R3JvdXBUb3BQZXJmb3JtZXJfQSxPcGVuRmlsdGVyVG9nZ2xlXzIsU3VnZ2VzdGlvblN0b3JlQWRzX0IsQmFkZ2VCb29zdF9BIn0=")
soup_wiiwipes = BeautifulSoup(r_wiiwipes.text, features="html.parser")
price_wiiwipes =soup_wiiwipes.find("div", {"class": "pr-bx-nm with-org-prc"}).find("span", {"class": "prc-dsc"})
price_wiiwipes_str = price_wiiwipes.text
price_wiiwipes_clean = re.search("[+-]?([0-9]*[,])?[0-9]+", price_wiiwipes_str).group(0).replace(",",".")
print(price_wiiwipes_clean)

r_hugo_baby_wipe = requests.get("https://www.trendyol.com/baby-turco/gul-kokulu-islak-bebek-havlusu-12x90-adet-p-136047394")
soup_hugo_baby_wipe = BeautifulSoup(r_hugo_baby_wipe.text, features="html.parser")
price_hugo_baby_wipe =soup_hugo_baby_wipe.find("div", {"class": "pr-bx-nm with-org-prc"}).find("span", {"class": "prc-dsc"})
price_hugo_baby_wipe_str = price_hugo_baby_wipe.text
price_hugo_baby_wipe_clean = re.search("[+-]?([0-9]*[,])?[0-9]+", price_hugo_baby_wipe_str).group(0).replace(",",".")
print(price_hugo_baby_wipe_clean)

############## Baby shampoo ##########################
r_jhonsonj = requests.get("https://www.trendyol.com/johnson-s/bedtime-sac-vucut-sampuani-500-ml-p-6581235?boutiqueId=61&merchantId=624129")
soup_jhonsonj = BeautifulSoup(r_jhonsonj.text, features="html.parser")
price_jhonsonj =soup_jhonsonj.find("div", {"class": "pr-bx-nm with-org-prc"}).find("span", {"class": "prc-dsc"})
price_jhonsonj_str = price_jhonsonj.text
price_jhonsonj_clean = re.search("[+-]?([0-9]*[,])?[0-9]+", price_jhonsonj_str).group(0).replace(",",".")
print(price_jhonsonj_clean)

r_mustela = requests.get("https://www.trendyol.com/mustela/sac-vucut-sampuan-500-ml-2-adet-set-p-34999578?boutiqueId=61&merchantId=199226")
soup_mustela = BeautifulSoup(r_mustela.text, features="html.parser")
price_mustela =soup_mustela.find("div", {"class": "pr-bx-nm with-org-prc"}).find("span", {"class": "prc-dsc"})
price_mustela_str = price_mustela.text
price_mustela_clean = re.search("[+-]?([0-9]*[,])?[0-9]+", price_mustela_str).group(0).replace(",",".")
print(price_mustela_clean)

r_chicco = requests.get("https://www.trendyol.com/chicco/baby-moments-dogal-sac-ve-vucut-sampuani-500ml-p-165284533?boutiqueId=61&merchantId=112535")
soup_chicco = BeautifulSoup(r_chicco.text, features="html.parser")
price_chicco =soup_chicco.find("div", {"class": "pr-bx-nm with-org-prc"}).find("span", {"class": "prc-dsc"})
price_chicco_str = price_chicco.text
price_chicco_clean = re.search("[+-]?([0-9]*[,])?[0-9]+", price_chicco_str).group(0).replace(",",".")
print(price_chicco_clean)

