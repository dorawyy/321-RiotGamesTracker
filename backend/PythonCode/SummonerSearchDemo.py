# -*- coding: utf-8 -*-
from riotwatcher import LolWatcher#, ApiError
import pandas as pd
import sys
import config

api_key = config.riot_games_api_key

watcher = LolWatcher(api_key)
region = "na1"

name = sys.argv[1]
printOption = sys.argv[2]
# print("summoner name = ", name)

summoner = watcher.summoner.by_name(region, name)

summoner_ranked_stats = watcher.league.by_summoner(region, summoner['id'])

if (printOption == "profile"):
    print(summoner_ranked_stats)

match_history = watcher.match.matchlist_by_account(region, summoner['accountId'])
# print(match_history['matches'][99])

last_match = match_history['matches'][0]

match_detail = watcher.match.by_id(region, last_match['gameId'])

# print("Match Detail")
print(match_detail)

# summoner_match_history = watcher.match.matchlist_by_account(region, summoner['id'])

# print(summoner_match_history)
# previous_match = summoner_match_history['matches'][0]

# match_detail = watcher.match.by_id(region, previous_match['gameId'])

participants = []

i = 0
for row in match_detail['participants']:
    participants_row = {}
    participants_row['participantID'] = row['stats']['participantId']
    participants_row['teamId'] = row['stats']['participantId']
    participants_row['champion'] = row['championId']
    participants_row['summonerSpell1'] = row['spell1Id']
    participants_row['summonerSpell2'] = row['spell2Id']

    participants_row['win'] = row['stats']['win']
    participants_row['kills'] = row['stats']['kills']
    participants_row['deaths'] = row['stats']['deaths']
    participants_row['assists'] = row['stats']['assists']
    participants_row['totalDamageDealt'] = row['stats']['totalDamageDealt']
    participants_row['goldEarned'] = row['stats']['goldEarned']
    participants_row['champLevel'] = row['stats']['champLevel']
    participants_row['totalMinionsKilled'] = row['stats']['totalMinionsKilled']
    participants_row['item0'] = row['stats']['item0']
    participants_row['item1'] = row['stats']['item1']

    participants_row['SummonerName'] = match_detail['participantIdentities'][i]['player']['summonerName']
    i = i + 1
    participants.append(participants_row)
# for row in match_detail['participantIdentities']:
    # print(row)
    # participants_row = {}
    # print(row['player']['summonerName'])
    # participants.append(participants_row)

latest = watcher.data_dragon.versions_for_region(region)['n']['champion']
static_champ_list = watcher.data_dragon.champions(latest, False, "en_US")

champ_dict = {}
for key in static_champ_list['data']:
    row = static_champ_list['data'][key]
    champ_dict[row['key']] = row['id']
for row in participants:
    # print(str(row['champion']) + ' ' + champ_dict[str(row['champion'])])
    row['championName'] = champ_dict[str(row['champion'])]

df = pd.DataFrame(participants)

# print(df)
if (printOption == "summoner"):
    json_df = df.to_json()
    print(f'{{"Summoner":{{"name":"{summoner["name"]}","summonerLevel":"{summoner["summonerLevel"]}"}},"MatchHistory":')
    print(json_df)
    print('}')

mastery = watcher.champion_mastery.by_summoner(region, summoner['id'])

chests = []

# for row in mastery['championId']:
#     print("hello")
#     chests_row = {}
    
# print(mastery)