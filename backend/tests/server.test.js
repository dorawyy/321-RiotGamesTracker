const supertest = require('supertest')
const app = require('../server.js')
const request = supertest(app)

describe('GET', function(){

    it('should return successfully', async done =>{
        const response = await request.get('/summoner?name=gunner62')
        expect(response.status).toBe(200)
        done()
        })

    it('should return successfully', async done =>{
        const response = await request.get('/profile?name=gunner62')
        expect(response.status).toBe(200)
        done()
        console.log(response)
        })

})