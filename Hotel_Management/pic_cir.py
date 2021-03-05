from PIL import Image
import numpy as np
import sys

import math

RESOLUTION = (100, 100)
def read_img(file_in):
    img = Image.open(file_in)
    img = img.resize(RESOLUTION)
    
    img_rgb = img
    img = img.convert("RGBA")
    return np.array(img_rgb), np.array(img)

def get_dist(x, y):
    return math.sqrt((y[0] - x[0]) * (y[0] - x[0]) + (y[1] - x[1]) * (y[1] - x[1]))

def in_circle(center, point, radius):
    return get_dist(center, point) <= radius

'''
def generate_mask():
    mask = np.zeros((RESOLUTION[0], RESOLUTION[1], 3), dtype = np.uint8)
    center = (RESOLUTION[0] // 2, RESOLUTION[1] // 2)
    radius = RESOLUTION[0] // 2
    
    for i in range(RESOLUTION[0]):
        for j in range(RESOLUTION[1]):
            if in_circle(center, (i,  j), radius):
                mask[i][j] = np.array([1, 1, 1])
            else:
                mask[i][j] = np.array([0, 0, 0])
                
    return mask
'''

def display_img(img_rgb, img, mask, file_in):
    #img_rgb = img_rgb * mask
    #mask = np.zeros((RESOLUTION[0], RESOLUTION[1], 3), dtype = np.uint8)
    
    center = (RESOLUTION[0] // 2, RESOLUTION[1] // 2)
    radius = RESOLUTION[0] // 2
    
    for i in range(RESOLUTION[0]):
        for j in range(RESOLUTION[1]):
            if not in_circle(center, (i,  j), radius):
                img[i][j] = np.array([0, 0, 0, 0])
    im = Image.fromarray(img)
    im.save(f'{file_in}_out.png', "PNG")

file_in = sys.argv[1]
image_rgb, image = read_img(file_in)
display_img(image_rgb, image, None, file_in)