import sys
import requests
import time

from bs4 import BeautifulSoup
import datetime
import re

#r = requests.get(sys.argv[1])
#soup = BeautifulSoup(r.text, features="html.parser")
#price = soup.find('span', class_='prc-org')
#price_str = price.text
#price_clean = re.search("[+-]?([0-9]*[,])?[0-9]+", price_str).group(0).replace(",",".")

#print(price_clean)

############## For Diapers ##########################

r_molfix = requests.get("https://www.cimri.com/bebek-bezi/en-ucuz-molfix-no4-maxi-100-adet-bebek-bezi-fiyatlari,263974358")
soup_molfix = BeautifulSoup(r_molfix.text, features="html.parser")
price_molfix =soup_molfix.find("span", {"class": "s1wl91l5-4 cBVHJG"})
price_molfix_str = price_molfix.text
price_molfix_clean = re.search("[+-]?([0-9]*[,])?[0-9]+", price_molfix_str).group(0).replace(",",".")
print(price_molfix_clean)


r_sleepy = requests.get("https://www.cimri.com/bebek-bezi/en-ucuz-sleepy-4-numara-maxi-plus-natural-bebek-bezi-fiyatlari,2134424328")
soup_sleepy = BeautifulSoup(r_sleepy.text, features="html.parser")
price_sleepy =soup_sleepy.find("span", {"class": "s1wl91l5-4 cBVHJG"})
price_sleepy_str = price_sleepy.text
price_sleepy_clean = re.search("[+-]?([0-9]*[,])?[0-9]+", price_sleepy_str).group(0).replace(",",".")
print(price_sleepy_clean)

r_turco = requests.get("https://www.cimri.com/bebek-mendil-islak-mendil/en-ucuz-baby-turco-dogadan-5-numara-junior-120-adet-3-x-60-dogadan-islak-havlu-seti-fiyatlari,751284667")
soup_turco = BeautifulSoup(r_turco.text, features="html.parser")
price_turco =soup_turco.find("span", {"class": "s1wl91l5-4 cBVHJG"})
price_turco_str = price_turco.text
price_turco_clean = re.search("[+-]?([0-9]*[,])?[0-9]+", price_turco_str).group(0).replace(",",".")
print(price_turco_clean)

r_prima = requests.get("https://www.cimri.com/bebek-bezi/en-ucuz-prima-premium-care-no1-yenidogan-70-adet-bebek-bezi-fiyatlari,502317495")
soup_prima = BeautifulSoup(r_prima.text, features="html.parser")
price_prima =soup_prima.find("span", {"class": "s1wl91l5-4 cBVHJG"})
price_prima_str = price_prima.text
price_prima_clean = re.search("[+-]?([0-9]*[,])?[0-9]+", price_prima_str).group(0).replace(",",".")
print(price_prima_clean)

r_pure_baby = requests.get("https://www.cimri.com/bebek-bezi/en-ucuz-pure-baby-maxi-4-numara-100-adet-bebek-bezi-fiyatlari,2104362081")
soup_pure_baby = BeautifulSoup(r_pure_baby.text, features="html.parser")
price_pure_baby =soup_pure_baby.find("span", {"class": "s1wl91l5-4 cBVHJG"})
price_pure_baby_str = price_pure_baby.text
price_pure_baby_clean = re.search("[+-]?([0-9]*[,])?[0-9]+", price_pure_baby_str).group(0).replace(",",".")
print(price_pure_baby_clean)

############## Baby Formula ##########################

r_aptamil = requests.get("https://www.cimri.com/bebek-mamalari/en-ucuz-aptamil-4-350-gr-devam-sutu-fiyatlari,182765625")
soup_aptamil = BeautifulSoup(r_aptamil.text, features="html.parser")
price_aptamil =soup_aptamil.find("span", {"class": "s1wl91l5-4 cBVHJG"})
price_aptamil_str = price_aptamil.text
price_aptamil_clean = re.search("[+-]?([0-9]*[,])?[0-9]+", price_aptamil_str).group(0).replace(",",".")
print(price_aptamil_clean)

r_herobaby = requests.get("https://www.cimri.com/en-ucuz-hero-baby-sutlu-8-tahilli-meyveli-kasik-mamasi-400-gr-6-herobaby-tahillimeyveli-400gr-fiyatlari,1863423919")
soup_herobaby = BeautifulSoup(r_herobaby.text, features="html.parser")
price_herobaby =soup_herobaby.find("span", {"class": "s1wl91l5-4 cBVHJG"})
price_herobaby_str = price_herobaby.text
price_herobaby_clean = re.search("[+-]?([0-9]*[,])?[0-9]+", price_herobaby_str).group(0).replace(",",".")
print(price_herobaby_clean)

r_bebelac = requests.get("https://www.cimri.com/bebek-mamalari/en-ucuz-bebelac-gold-2-800-gr-6-12-ay-devam-sutu-fiyatlari,1216953046")
soup_bebelac = BeautifulSoup(r_bebelac.text, features="html.parser")
price_bebelac =soup_bebelac.find("span", {"class": "s1wl91l5-4 cBVHJG"})
price_bebelac_str = price_bebelac.text
price_bebelac_clean = re.search("[+-]?([0-9]*[,])?[0-9]+", price_bebelac_str).group(0).replace(",",".")
print(price_bebelac_clean)

r_similac = requests.get("https://www.cimri.com/bebek-mamalari/en-ucuz-similac-3-850-gr-devam-sutu-fiyatlari,18890343")
soup_similac = BeautifulSoup(r_similac.text, features="html.parser")
price_similac =soup_similac.find("span", {"class": "s1wl91l5-4 cBVHJG"})
price_similac_str = price_similac.text
price_similac_clean = re.search("[+-]?([0-9]*[,])?[0-9]+", price_similac_str).group(0).replace(",",".")
print(price_similac_clean)

r_bebevita = requests.get("https://www.cimri.com/en-ucuz-bebivita-bebevita-1-bebek-sutu-dogumdan-itibaren-500-x-2-adet-dp37652-fiyatlari,2160979390")
soup_bebevita = BeautifulSoup(r_bebevita.text, features="html.parser")
price_bebevita =soup_bebevita.find("span", {"class": "s1wl91l5-4 cBVHJG"})
price_bebevita_str = price_bebevita.text
price_bebevita_clean = re.search("[+-]?([0-9]*[,])?[0-9]+", price_bebevita_str).group(0).replace(",",".")
print(price_bebevita_clean)

r_sma = requests.get("https://www.cimri.com/bebek-mamalari/en-ucuz-sma-optipro-2-probiyotik-400-gr-devam-sutu-fiyatlari,1389747315")
soup_sma = BeautifulSoup(r_sma.text, features="html.parser")
price_sma =soup_sma.find("span", {"class": "s1wl91l5-4 cBVHJG"})
price_sma_str = price_sma.text
price_sma_clean = re.search("[+-]?([0-9]*[,])?[0-9]+", price_sma_str).group(0).replace(",",".")
print(price_sma_clean)

r_lactamil = requests.get("https://www.cimri.com/sut-artirici-icecek/en-ucuz-milupa-lactamil-200-gr-emziren-anneler-icin-sutlu-icecek-fiyatlari,609314")
soup_lactamil = BeautifulSoup(r_lactamil.text, features="html.parser")
price_lactamil =soup_lactamil.find("span", {"class": "s1wl91l5-4 cBVHJG"})
price_lactamil_str = price_lactamil.text
price_lactamil_clean = re.search("[+-]?([0-9]*[,])?[0-9]+", price_lactamil_str).group(0).replace(",",".")
print(price_lactamil_clean)

############## Baby Wet Wipes ##########################

r_uni_baby = requests.get("https://www.cimri.com/bebek-mendil-islak-mendil/en-ucuz-uni-baby-wipes-90x12li-1080-yaprak-islak-havlu-fiyatlari,113380607")
soup_uni_baby = BeautifulSoup(r_uni_baby.text, features="html.parser")
price_uni_baby =soup_uni_baby.find("span", {"class": "s1wl91l5-4 cBVHJG"})
price_uni_baby_str = price_uni_baby.text
price_uni_baby_clean = re.search("[+-]?([0-9]*[,])?[0-9]+", price_uni_baby_str).group(0).replace(",",".")
print(price_uni_baby_clean)

r_sleepy_wipe = requests.get("https://www.cimri.com/bebek-mendil-islak-mendil/en-ucuz-sleepy-natural-yenidogan-40x12li-480-yaprak-islak-havlu-fiyatlari,464749130")
soup_sleepy_wipe = BeautifulSoup(r_sleepy_wipe.text, features="html.parser")
price_sleepy_wipe =soup_sleepy_wipe.find("span", {"class": "s1wl91l5-4 cBVHJG"})
price_sleepy_wipe_str = price_sleepy_wipe.text
price_sleepy_wipe_clean = re.search("[+-]?([0-9]*[,])?[0-9]+", price_sleepy_wipe_str).group(0).replace(",",".")
print(price_sleepy_wipe_clean)

r_babyturco_wipe = requests.get("https://www.cimri.com/bebek-mendil-islak-mendil/en-ucuz-baby-turco-12x120-adet-softcare-aloe-vera-islak-bebek-havlusu-fiyatlari,902634676")
soup_babyturco_wipe =BeautifulSoup(r_sleepy_wipe.text, features="html.parser")
price_babyturco_wipe =soup_babyturco_wipe.find("span", {"class": "s1wl91l5-4 cBVHJG"})
price_babyturco_wipe_str = price_babyturco_wipe.text
price_babyturco_wipe_clean = re.search("[+-]?([0-9]*[,])?[0-9]+", price_babyturco_wipe_str).group(0).replace(",",".")
print(price_babyturco_wipe_clean)

r_molfix_wipe = requests.get("https://www.cimri.com/bebek-mendil-islak-mendil/en-ucuz-molfix-izotonik-sulu-ferah-temizlik-60-yaprak-24lu-paket-1440-adet-islak-mendil-fiyatlari,418416079")
soup_molfix_wipe = BeautifulSoup(r_molfix_wipe.text, features="html.parser")
price_molfix_wipe =soup_molfix_wipe.find("span", {"class": "s1wl91l5-4 cBVHJG"})
price_molfix_wipe_str = price_molfix_wipe.text
price_molfix_wipe_clean = re.search("[+-]?([0-9]*[,])?[0-9]+", price_molfix_wipe_str).group(0).replace(",",".")
print(price_molfix_wipe_clean)


r_wiiwipes = requests.get("https://www.cimri.com/en-ucuz-bii-wipes-sensitive-90li-hassas-bakim-islak-bebek-havlusu-24-paket-01ih001-fiyatlari,1690440663")
soup_wiiwipes = BeautifulSoup(r_wiiwipes.text, features="html.parser")
price_wiiwipes =soup_wiiwipes.find("span", {"class": "s1wl91l5-4 cBVHJG"})
price_wiiwipes_str = price_wiiwipes.text
price_wiiwipes_clean = re.search("[+-]?([0-9]*[,])?[0-9]+", price_wiiwipes_str).group(0).replace(",",".")
print(price_wiiwipes_clean)

r_hugo_baby_wipe = requests.get("https://www.cimri.com/bebek-mendil-islak-mendil/en-ucuz-baby-turco-24x90-adet-gul-kokulu-bebek-islak-mendil-fiyatlari,915296843")
soup_hugo_baby_wipe = BeautifulSoup(r_hugo_baby_wipe.text, features="html.parser")
price_hugo_baby_wipe =soup_hugo_baby_wipe.find("span", {"class": "s1wl91l5-4 cBVHJG"})
price_hugo_baby_wipe_str = price_hugo_baby_wipe.text
price_hugo_baby_wipe_clean = re.search("[+-]?([0-9]*[,])?[0-9]+", price_hugo_baby_wipe_str).group(0).replace(",",".")
print(price_hugo_baby_wipe_clean)

############## Baby shampoo ##########################
r_jhonsonj = requests.get("https://www.cimri.com/en-ucuz-jhonsons-baby-bedtime-sampuan-500ml-rahatlatici-1435-fiyatlari,904525095")
soup_jhonsonj = BeautifulSoup(r_jhonsonj.text, features="html.parser")
price_jhonsonj =soup_jhonsonj.find("span", {"class": "s1wl91l5-4 cBVHJG"})
price_jhonsonj_str = price_jhonsonj.text
price_jhonsonj_clean = re.search("[+-]?([0-9]*[,])?[0-9]+", price_jhonsonj_str).group(0).replace(",",".")
print(price_jhonsonj_clean)

r_mustela = requests.get("https://www.cimri.com/bebek-sampuani/en-ucuz-mustela-2x500-ml-bebek-sac-ve-vucut-sampuani-fiyatlari,160314420")
soup_mustela = BeautifulSoup(r_mustela.text, features="html.parser")
price_mustela =soup_mustela.find("span", {"class": "s1wl91l5-4 cBVHJG"})
price_mustela_str = price_mustela.text
price_mustela_clean = re.search("[+-]?([0-9]*[,])?[0-9]+", price_mustela_str).group(0).replace(",",".")
print(price_mustela_clean)

r_chicco = requests.get("https://www.cimri.com/bebek-pudra/en-ucuz-chicco-150-gr-baby-moments-dogal-bebek-talk-pudrasi-fiyatlari,1948766717")
soup_chicco = BeautifulSoup(r_chicco.text, features="html.parser")
price_chicco =soup_chicco.find("span", {"class": "s1wl91l5-4 cBVHJG"})
price_chicco_str = price_chicco.text
price_chicco_clean = re.search("[+-]?([0-9]*[,])?[0-9]+", price_chicco_str).group(0).replace(",",".")
print(price_chicco_clean)

