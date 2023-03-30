import requests
import time
from pprint import pprint
from bs4 import BeautifulSoup
import datetime
import tkinter as tk


class Product:
    def __init__(self, url, price_change_threshold):
        self.url = url
        self.price_change_threshold = price_change_threshold
        self.current_price = None
        self.previous_price = None
        self.price_history = []

    def get_price(self):
        pass

    def check_price_change(self):
        pass

    def send_notification(self, message):
        pass


class Pamuk(Product):    
    def get_price(self):         
        r = requests.get(self.url)         
        soup = BeautifulSoup(r.text, features="html.parser")         
        pamuk_price = soup.find('p', class_='price')       
        price_str = pamuk_price.text         
        pamuk_price = float(price_str.replace(',', '.').replace(' TL', ''))         
        self.previous_price = 3.55         
        self.current_price = round(pamuk_price, 2)         
        self.price_history.append(self.current_price)         
        print("The current price for Pamuk is : " + str(self.current_price)+" TL")         
        return self.current_price     

    def check_price_change(self):        
        price_diff = abs(self.current_price - self.previous_price)         
        if price_diff > self.price_change_threshold:             
            message = "Pamuk fiyatı Değişiyor!"             
            self.send_notification(message)     
            
    def send_notification(self, message):         
        TOKEN = '5600475635:AAFS_BFrajktfTJlx-7yVxlqFVD39zTqjo0'         
        chat_id = '5521966110'        
        url = f"https://api.telegram.org/bot{TOKEN}/sendMessage?chat_id={chat_id}&text={message}"         
        requests.get(url)


class BebekBezi(Product):    
    def get_price(self):         
        r = requests.get(self.url)         
        soup = BeautifulSoup(r.text, features="html.parser")    
             
        Bez_price = soup.find('span', class_='s1wl91l5-4 cBVHJG')   
           
        price_str = Bez_price.text         
        Bez_price = float(price_str.replace(',', '.').replace(' TL', ''))         
        self.previous_price = 264         
        self.current_price = int(Bez_price)         
        self.price_history.append(self.current_price)         
        print("The current price for Bez is : " + str(self.current_price)+" TL")         
        return self.current_price     
    def check_price_change(self):        
        price_diff = abs(self.current_price - self.previous_price)         
        if price_diff > self.price_change_threshold:             
            message = "Bez fiyatı Değişiyor!"             
            self.send_notification(message)     
    def send_notification(self, message):         
        TOKEN = '5600475635:AAFS_BFrajktfTJlx-7yVxlqFVD39zTqjo0'         
        chat_id = '5521966110'        
        url = f"https://api.telegram.org/bot{TOKEN}/sendMessage?chat_id={chat_id}&text={message}"         
        requests.get(url)


class GUI:
    def __init__(self):
        self.window = tk.Tk()
        self.window.title("Check Price")
        self.window.geometry("500x500")
        
        self.price_label_pamuk = tk.Label(self.window, text="Pamuk Price: ")
        self.price_label_pamuk.pack(pady=20)

        self.price_label_bez = tk.Label(self.window, text="Bez Price: ")
        self.price_label_bez.pack(pady=20)
        
        self.check_price_button = tk.Button(self.window, text="Check Price", command=self.check_price)
        self.check_price_button.pack(pady=10)
        
    def check_price(self):
        pamuk = Pamuk("https://www.e-bebek.com/baby-me-bebek-temizleme-pamugu-15x60-adet-p-bae-8992", 10)
        bez= BebekBezi("https://www.cimri.com/bebek-bezi/en-ucuz-sleepy-natural-no3-midi-168-adet-bebek-bezi-fiyatlari,2055343252",10)
        current_price_pamuk = pamuk.get_price()
        current_price_bez = bez.get_price()
        self.price_label_pamuk.config(text="Pamuk Price: {} TL".format(current_price_pamuk))
        self.price_label_bez.config(text="Bez Price: {} TL".format(current_price_bez))

gui = GUI()
gui.window.mainloop()


def main():
    root = tk.Tk()
    #app = Application(master=root)
    #app.mainloop()


if __name__ == "__main__":
    main()
