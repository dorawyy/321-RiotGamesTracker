const request = require('supertest')
const express = require('express');
jest.mock('express')

const server = require('../server.js')

describe('GET /summoner', function(){
    afterEach(()=> {
        server.close()
    });
    test('Mock GET req', () => {
        const mockRespond = { data : {
            participantID: 12,
            teamId: 2,
            champion: "naruto",
            summonerSpell1: "run",
            summonerSpell2: "sage",
            win: 2,
            kills: 2,
            deaths: 1,
            assists: 1,
            totalDamageDealt: 433,
            goldEarned: 1,
            champLevel: 12,
            totalMinionsKilled: 2,
            item0: "katana",
            item1: "shuriken"}
        }
        server.get.mockResolvedValue(mockRespond)
        request(server).get('/summoner').expect(200)
        })
})