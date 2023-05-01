import { DeviceUUID } from 'device-uuid';

class DeviceService {
  getDeviceUUID() {
    let uuid = new DeviceUUID().get();
    console.log("Device UUID: " + uuid);
    return uuid;
  }
}

export default new DeviceService();
