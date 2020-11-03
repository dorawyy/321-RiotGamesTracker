const supertest = require('supertest')
const server = require('../server.js')
const request = supertest(server)

describe('GET', function(){

    afterEach(()=> {
        server.close()
    });

    it('should return successfully', async done =>{
        jest.setTimeout(10000);
        const response = await request.get('/summoner?name=gunner62')
        expect(response.status).toBe(200)
        done()
        });

    it('should return successfully', async done =>{
        jest.setTimeout(10000);
        const response = await request.get('/profile?name=gunner62')
        expect(response.status).toBe(200)
        done()
        });

})