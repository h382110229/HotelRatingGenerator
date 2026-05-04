import os
from PIL import Image, ImageDraw
import glob

# Source image
src_img_path = "/Users/hawk/.gemini/antigravity/brain/1967db02-6f73-4180-8752-03603279ff06/flat_luxury_hawk_icon_1777883308064.png"

# Target directories and sizes
sizes = {
    "mdpi": 48,
    "hdpi": 72,
    "xhdpi": 96,
    "xxhdpi": 144,
    "xxxhdpi": 192
}

base_dir = "app/src/main/res"

def create_circular_mask(h, w):
    mask = Image.new("L", (w, h), 0)
    draw = ImageDraw.Draw(mask)
    draw.ellipse((0, 0, w, h), fill=255)
    return mask

try:
    img = Image.open(src_img_path).convert("RGBA")
    
    # Remove existing webp icons
    for webp_file in glob.glob(f"{base_dir}/mipmap-*/ic_launcher*.webp"):
        os.remove(webp_file)

    for density, size in sizes.items():
        out_dir = os.path.join(base_dir, f"mipmap-{density}")
        os.makedirs(out_dir, exist_ok=True)
        
        # Resize image
        resized_img = img.resize((size, size), Image.Resampling.LANCZOS)
        
        # Save square/normal icon
        resized_img.save(os.path.join(out_dir, "ic_launcher.png"))
        
        # Create and save round icon
        mask = create_circular_mask(size, size)
        round_img = resized_img.copy()
        round_img.putalpha(mask)
        round_img.save(os.path.join(out_dir, "ic_launcher_round.png"))
        
    print("Successfully generated all mipmap icons!")
except Exception as e:
    print(f"Error: {e}")
