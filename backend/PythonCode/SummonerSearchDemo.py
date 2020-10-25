# -*- coding: utf-8 -*-
"""
Created on Sat Oct 24 19:56:18 2020

@author: wccha
"""

from riotwatcher import LolWatcher, ApiError
import pandas as pd
import sys

api_key = "RGAPI-0e99c4af-e2b5-4db2-87cc-843718bb0e7c"

watcher = LolWatcher(api_key)
region = "na1"

# name = sys.argv[1]
# print("Summoner Name = ", name)

me = watcher.summoner.by_name(region, "Gunner62")
print(me)


