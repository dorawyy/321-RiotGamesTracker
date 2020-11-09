# -*- coding: utf-8 -*-
"""
Created on Sun Nov  1 16:13:12 2020

@author: wccha
"""

from riotwatcher import LolWatcher, ApiError
import pandas as pd
import sys
import config


api_key = config.riot_games_api_key

watcher = LolWatcher(api_key)
region = "na1"

name = sys.argv[1]
printOption = sys.argv[2]

summoner = watcher.summoner.by_name(region, name)

match_history = watcher.match.matchlist_by_account(region, summoner['accountId'], begin_index=0, end_index=50)

current_match = match_history['matches'][0]
match_detail = watcher.match.by_id(region, current_match['gameId'])
print(match_detail)

i = 0;
for match in match_history['matches']:    
    print(match)
    print(i)
    
    
    # match_detail = watcher.match.by_id(region, match['gameId'])
    i = i + 1
    
    