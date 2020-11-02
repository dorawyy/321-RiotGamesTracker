const router = require('../server');

const useSpy = jest.fn();
const listenSpy = jest.fn();

jest.doMock('express', () => {
  return () => ({
    use: useSpy,
    listen: listenSpy,
  })
});

describe('should test server configuration', () => {
  test('should call listen fn', () => {
    require('../server.js');
    expect(listenSpy).toHaveBeenCalled();
  });
});