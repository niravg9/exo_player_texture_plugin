
import 'temp_project_platform_interface.dart';

class TempProject {
  Future<String?> getPlatformVersion() {
    return TempProjectPlatform.instance.getPlatformVersion();
  }

  Future<int?> initPlayerAndGetTextureId() {
    return TempProjectPlatform.instance.initPlayerAndGetTextureId();
  }
}
