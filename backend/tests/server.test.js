
const supertest = require('supertest')
const s = require('../server.js')
const server = s.server;

const request = supertest(server)


describe('GET', function(){

    afterEach(()=> {
        server.close()
    });

    it('should return successfully', async done =>{
        jest.setTimeout(10000);
        const response = await request.get('/summoner?name=gunner62')
        console.log(response.text)
        expect(response.status).toBe(200)
        done()
        });

    it('should return successfully', async done =>{
        jest.setTimeout(10000);
        const response = await request.get('/profile?name=gunner62')
        console.log(response.text);
        expect(response.status).toBe(200)
        done()
        });

    it('should return successfully', async done =>{
        jest.setTimeout(10000);
        const response = await request.get('/recommend?name=gunner62&games=20')
        console.log(response.text)
        expect(response.status).toBe(200)
        done()
        });

    it('should return successfully', async done =>{
        jest.setTimeout(10000);
        const response = await request.get('/recommend?name=aayush&games=20')
        console.log(response.text)
        expect(response.status).toBe(200)
        done()
        });

    it('non exsistent user', async done =>{
        jest.setTimeout(10000);
        const response = await request.get('/recommend?name=  &games=20')
        console.log(response.text)
        console.log(response.body)
        expect(response.status).toBe(200)
        done()
        });

})
