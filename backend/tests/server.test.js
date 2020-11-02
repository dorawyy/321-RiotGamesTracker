const Controller = require('../server');

const getSpy = jest.fn();

jest.doMock('express', () => {
  return {
    Router() {
      return {
        get: getSpy,
      }
    }
  }
});

describe('should test router', () => {
  require('../server.js');
  test('should test get POSTS', () => {
    expect(getSpy).toHaveBeenNthCalledWith('/summoner');
  });
});


/* const express = require('express')
const app_express = express()
jest.mock('express');

describe('GET', () => {

    it('should return successfully', async done =>{
        
        const response = {data : {id: 123}}
        app_express.get.mockResolvedValue(response)

        const app = require('../server.js')

        app.get('/summoner')
        done()
        })

})

 */