#include <jni.h>
#include <iostream>
#include <vector>

extern "C" JNIEXPORT jobjectArray JNICALL Java_Convolution2D_nativeConvolution(JNIEnv *env, jclass, jobjectArray matrix, jobjectArray kernel) {
    int matrixSize = env->GetArrayLength(matrix);
    int kernelSize = env->GetArrayLength(kernel);
    int kernelRadius = kernelSize / 2;

    std::vector<std::vector<float>> matrixData(matrixSize, std::vector<float>(matrixSize));
    std::vector<std::vector<float>> kernelData(kernelSize, std::vector<float>(kernelSize));
    std::vector<std::vector<float>> result(matrixSize, std::vector<float>(matrixSize));

    for (int i = 0; i < matrixSize; i++) {
        jfloatArray row = (jfloatArray)env->GetObjectArrayElement(matrix, i);
        float* rowData = env->GetFloatArrayElements(row, 0);
        for (int j = 0; j < matrixSize; j++) {
            matrixData[i][j] = rowData[j];
        }
        env->ReleaseFloatArrayElements(row, rowData, 0);
    }

    for (int i = 0; i < kernelSize; i++) {
        jfloatArray row = (jfloatArray)env->GetObjectArrayElement(kernel, i);
        float* rowData = env->GetFloatArrayElements(row, 0);
        for (int j = 0; j < kernelSize; j++) {
            kernelData[i][j] = rowData[j];
        }
        env->ReleaseFloatArrayElements(row, rowData, 0);
    }

    for (int i = 0; i < matrixSize; i++) {
        for (int j = 0; j < matrixSize; j++) {
            float sum = 0;
            for (int k = 0; k < kernelSize; k++) {
                for (int l = 0; l < kernelSize; l++) {
                    int x = i + k - kernelRadius;
                    int y = j + l - kernelRadius;
                    if (x >= 0 && x < matrixSize && y >= 0 && y < matrixSize) {
                        sum += matrixData[x][y] * kernelData[k][l];
                    }
                }
            }
            result[i][j] = sum;
        }
    }

    jclass floatArrayClass = env->FindClass("[F");
    jobjectArray output = env->NewObjectArray(matrixSize, floatArrayClass, nullptr);
    for (int i = 0; i < matrixSize; i++) {
        jfloatArray row = env->NewFloatArray(matrixSize);
        env->SetFloatArrayRegion(row, 0, matrixSize, &result[i][0]);
        env->SetObjectArrayElement(output, i, row);
        env->DeleteLocalRef(row);
    }

    return output;
}
