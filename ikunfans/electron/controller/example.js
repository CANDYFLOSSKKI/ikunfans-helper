'use strict';

const { Controller } = require('ee-core');
const Log = require('ee-core/log');
const Services = require('ee-core/services');

/**
 * example
 * @class
 */
class ExampleController extends Controller {

  constructor(ctx) {
    super(ctx);
  }


  /**
   * 所有方法接收两个参数
   * @param args 前端传的参数
   * @param event - ipc通信时才有值。详情见：控制器文档
   */

  /**
   * test
   */
  async test() {
    const result = await Services.get('example').test('electron');
    Log.info('service result:', result);
    return 'hello electron-egg';
  }

  async close() {
    const Electron = require('ee-core/electron');
    Electron.mainWindow.close();
  }

  async tran(url) {
    const {shell} = require('electron');
    await shell.openExternal(url.url);
  }

}

ExampleController.toString = () => '[class ExampleController]';
module.exports = ExampleController;
