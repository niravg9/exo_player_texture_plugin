import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'temp_project_platform_interface.dart';

/// An implementation of [TempProjectPlatform] that uses method channels.
class MethodChannelTempProject extends TempProjectPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('temp_project');

  @override
  Future<String?> getPlatformVersion() async {
    final version = await methodChannel.invokeMethod<String>('getPlatformVersion');
    return version;
  }

  @override
  Future<int?> initPlayerAndGetTextureId() async {
    int? version = await methodChannel.invokeMethod<int>('initPlayerAndGetTextureId');
    return version;
  }

}
