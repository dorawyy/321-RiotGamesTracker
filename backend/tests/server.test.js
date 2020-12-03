
const supertest = require('supertest')
const server = require('../server.js')
const request = supertest(server)

const { db } = require('../model/FollowerSchema.js');


describe('GET', function(){

    afterEach(()=> {
        server.close()
        db.close()
    });

    it('summoner', async done =>{
        jest.setTimeout(10000);
        const response = await request.get('/summoner?name=gunner62')
        expect(response.status).toBe(200)
        expect(JSON.parse(response.text).Summoner.name).toBe('Gunner62')
        done()
        });
    
    it('profile', async done =>{
        jest.setTimeout(10000);
        const response = await request.get('/profile?name=gunner62')
        expect(response.status).toBe(200)
        expect(response.text.includes("'summonerName': 'Gunner62'")).toBeTruthy()
        done()
        });

    it('recommed valid', async done =>{
        jest.setTimeout(10000);
        const response = await request.get('/recommend?name=Gunner62&games=20')
        //console.log(response.text)
        expect(response.status).toBe(200)
        done()
        });

    it('non exsistent user', async done =>{
        jest.setTimeout(10000);
        const response = await request.get('/recommend?name=x&games=20')
        expect(response.text.includes("404 Client Error")).toBeTruthy()
        expect(response.status).toBe(200)
        done()
        });

})
