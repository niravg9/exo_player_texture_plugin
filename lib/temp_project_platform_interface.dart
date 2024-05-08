import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'temp_project_method_channel.dart';

abstract class TempProjectPlatform extends PlatformInterface {
  /// Constructs a TempProjectPlatform.
  TempProjectPlatform() : super(token: _token);

  static final Object _token = Object();

  static TempProjectPlatform _instance = MethodChannelTempProject();

  /// The default instance of [TempProjectPlatform] to use.
  ///
  /// Defaults to [MethodChannelTempProject].
  static TempProjectPlatform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [TempProjectPlatform] when
  /// they register themselves.
  static set instance(TempProjectPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<String?> getPlatformVersion() {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }

  Future<int?> initPlayerAndGetTextureId() {
    throw UnimplementedError('initPlayerAndGetTextureId() has not been implemented.');
  }
}
