import sys

def sample_func():
    try:
        sys.stdout.write("Python working good.")
        return 0
    except Exception as err:
        sys.stderr.write(f'Exception: {err}')
        return 1

# if __name__ == "__main__":
    # sample_func()
import tensorflow as tf
tf.config.list_physical_devices('GPU')

if tf.test.gpu_device_name() != '/device:GPU:0':
    print("We'll just use the CPU for this run.")
else:
    print('Huzzah! Found GPU: {}'.format(tf.test.gpu_device_name()))