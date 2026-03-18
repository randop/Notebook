"""
First training:
1. A dataset called the Oxford-IIIT Pet Dataset that 
    contains 7,349 images of cats and dogs from 37
    different breeds will be downloaded from the
    fast.ai datasets collection to the GPU server you are using,
    and will then be extracted.
2. A pretrained model that has already been trained
    on 1.3 million images, using a competition-winning model
    will be downloaded from the internet.
3. The pretrained model will be fine-tuned using the
    latest advances in transfer learning,
    to create a model that is specially customized
    for recognizing dogs and cats.
"""

import fastbook
fastbook.setup_book()

from fastbook import *

from fastai.vision.all import *

path = untar_data(URLs.PETS) / "images"

def is_cat(x):
    return x[0].isupper()

dls = ImageDataLoaders.from_name_func(
    path,
    get_image_files(path),
    valid_pct=0.2,
    seed=42,
    label_func=is_cat,
    item_tfms=Resize(224),
)

learn = vision_learner(dls, resnet34, metrics=error_rate)
learn.fine_tune(1)

