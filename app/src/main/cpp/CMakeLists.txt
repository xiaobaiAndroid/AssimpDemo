# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.

cmake_minimum_required(VERSION 3.18.1)

# Declares and names the project.

# 支持gnu++11
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=gnu++11")

project("assimpdemo")


set(OPENGL_LIB GLESv3)

set(libs "${CMAKE_SOURCE_DIR}/../jniLibs")

include_directories(
        include
        common
        modelAssimp
        jni
)

file(GLOB src-files
        ${CMAKE_SOURCE_DIR}/*.cpp

        ${CMAKE_SOURCE_DIR}/*/*.cpp

        ${CMAKE_SOURCE_DIR}/*/*/*.cpp

        ${CMAKE_SOURCE_DIR}/*/*/*/*.cpp

        ${CMAKE_SOURCE_DIR}/*/*/*/*/*.cpp

        ${CMAKE_SOURCE_DIR}/*/*/*/*/*/*.cpp

        ${CMAKE_SOURCE_DIR}/*/*/*/*/*/*/*.cpp
        )


#设置OpenCV动态库
add_library(libopencv SHARED IMPORTED)
set_target_properties(libopencv PROPERTIES IMPORTED_LOCATION
        ${libs}/${ANDROID_ABI}/libopencv_java4.so)

# =====  ASSIMP  =====
add_library(libassimp SHARED IMPORTED)
set_target_properties(libassimp PROPERTIES
        IMPORTED_LOCATION "${libs}/${ANDROID_ABI}/libassimp.so")



add_library( # Sets the name of the library.
        assimpdemo

        # Sets the library as a shared library.
        SHARED

        # Provides a relative path to your source file(s).
        ${src-files})


find_library( # Sets the name of the path variable.
        log-lib

        # Specifies the name of the NDK library that
        # you want CMake to locate.
        log)



target_link_libraries( # Specifies the target library.
        assimpdemo

        ${OPENGL_LIB}
        libassimp
        libopencv
        # Links the target library to the log library
        # included in the NDK.
        android

        #使本机代码安全地访问Java对象的像素缓冲区的位图
        jnigraphics
        # Links the target library to the log library
        # included in the NDK.
        ${log-lib})