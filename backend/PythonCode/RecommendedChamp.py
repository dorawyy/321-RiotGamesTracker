# -*- coding: utf-8 -*-
from riotwatcher import LolWatcher#, ApiError
import pandas as pd
import sys
import config

api_key = config.riot_games_api_key

watcher = LolWatcher(api_key)
region = "na1"

name = sys.argv[1]
endIndex = sys.argv[2]

summoner = watcher.summoner.by_name(region, name)

match_history = watcher.match.matchlist_by_account(region, summoner['accountId'], begin_index=0, end_index=endIndex)

current_match = match_history['matches'][0]
# match_detail = watcher.match.by_id(region, current_match['gameId'])
# print(match_detail)

latest = watcher.data_dragon.versions_for_region(region)['n']['champion']
static_champ_list = watcher.data_dragon.champions(latest, False, "en_US")


i = 0;

past_games = []

for match in match_history['matches']:
    current_match = {}

    # print(match)
    match_detail = watcher.match.by_id(region, match['gameId'])

    for participant in match_detail['participantIdentities']:
        #Find the participant ID of the user, name should exist since it passed previous calls
        if (participant['player']['summonerName'] == name):
            current_match['ID'] = participant['participantId']
            # print(participant['player']['summonerName'])
            # print(current_match['ID'])
    #Hooray for 1 based indexing
    current_player = match_detail['participants'][current_match['ID'] - 1]
    player_stats = current_player['stats']
    current_match['championID'] = current_player['championId']
    
    current_match['win'] = player_stats['win']
    current_match['KDA'] = (player_stats['kills'] + player_stats['assists']) / (player_stats['deaths'] if player_stats['deaths'] != 0 else 1)
    current_match['damageDealt'] = player_stats['totalDamageDealtToChampions']
    current_match['gold'] = player_stats['goldEarned']
    
    # print(current_match['championID'])
    
    # print(current_match['win'])
    i = i + 1
    past_games.append(current_match)
    

champ_dict = {}
for key in static_champ_list['data']:
    row = static_champ_list['data'][key]
    champ_dict[row['key']] = row['id']
for row in past_games:
        # print(str(row['champion']) + ' ' + champ_dict[str(row['champion'])])
        row['championName'] = champ_dict[str(row['championID'])]
        del row['championID']
        del row['ID']
        

df = pd.DataFrame(past_games)

#Recommended champ based off only 1 field currently, will add more later

recommendedChampion = df['championName'][df['KDA'].argmax()]

print(recommendedChampion)