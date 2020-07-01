def proc(name):
	img=Image.open("{}.png".format(name))
	for i in range(8):
		img.crop((i*32,0,i*32+32,48)).save("{}_center_{}.png".format(name,i))
	for i in range(4):
		img.crop((i*32,48,i*32+32,96)).save("{}_toLeft_{}.png".format(name,i))
	for i in range(4):
		img.crop(((i+4)*32,48,(i+4)*32+32,96)).save("{}_left_{}.png".format(name,i))
	for i in range(4):
		img.crop((i*32,96,i*32+32,144)).save("{}_toRight_{}.png".format(name,i))
	for i in range(4):
		img.crop(((i+4)*32,96,(i+4)*32+32,144)).save("{}_right_{}.png".format(name,i))
