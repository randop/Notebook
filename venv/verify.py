import tensorflow as tf
from keras import backend as K

print(tf.test.is_gpu_available())
print(K.tensorflow_backend._get_available_gpus())
