#!/usr/bin/env python3
"""Draws placeholder textures from sheets/pals.json (body + head accent + eyes)."""
import json, os
from PIL import Image, ImageDraw
ROOT = os.path.join(os.path.dirname(os.path.abspath(__file__)), "..")
OUT = os.path.join(ROOT, "src/main/resources/assets/palcraft/textures")
rgb = lambda n: ((n >> 16) & 255, (n >> 8) & 255, n & 255)
for p in json.load(open(os.path.join(ROOT, "sheets/pals.json")))["rows"]:
    body, acc = rgb(p["bodyColor"]), rgb(p["accentColor"])
    im = Image.new("RGBA", (64, 32), body + (255,))
    d = ImageDraw.Draw(im)
    d.rectangle([0, 18, 27, 31], fill=acc + (255,))        # head region
    d.rectangle([28, 18, 39, 31], fill=tuple(int(c * .8) for c in body) + (255,))  # legs
    d.rectangle([6, 25, 6, 26], fill=(20, 20, 20, 255))    # eyes on head front face (u5..10, v23..28)
    d.rectangle([9, 25, 9, 26], fill=(20, 20, 20, 255))
    im.save(os.path.join(OUT, "entity", p["id"] + ".png"))
# Pal Sphere item: red top, white bottom, black band, white button
im = Image.new("RGBA", (16, 16), (0, 0, 0, 0)); d = ImageDraw.Draw(im)
d.ellipse([1, 1, 14, 14], fill=(230, 40, 40, 255), outline=(30, 30, 30, 255))
d.pieslice([1, 1, 14, 14], 0, 180, fill=(245, 245, 245, 255), outline=(30, 30, 30, 255))
d.line([1, 7, 14, 7], fill=(30, 30, 30, 255), width=2)
d.ellipse([6, 6, 9, 9], fill=(255, 255, 255, 255), outline=(30, 30, 30, 255))
im.save(os.path.join(OUT, "item", "pal_sphere.png"))
print("textures written")
