const request = require('supertest')
const app = require('../server.js')

describe('GET /input', function(){
    it('should return successfully', function(done){
        request(app)
        .get('/input')
        .expect(200)
        .end(done)
        })
})

describe('GET /hello', function(){
    it('should return successfully', function(done){
        request(app)
        .get('/hello')
        .expect(200)
        .end(done)
        })
})

describe('GET /param', function(){
    it('should return successfully', function(done){
        request(app)
        .get('/param')
        .expect(200)
        .end(done)
        })
})

describe('GET /summoner', function(){
    it('should return successfully', function(done){
        request(app)
        .get('/summoner')
        .expect(200)
        .end(done)
        })
})
