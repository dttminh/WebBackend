package com.roojai


class Piramid {
	static void main(args){
		def piramidSize=39;
		def start = (piramidSize+1)/2
		for(int i=0;i<piramidSize;i++){
			if( start == 0 )
				break
			for(int k=0;k<start-1;k++)
				print "*";
			start -=1
			
			def point = (i*2)+1
			for(int j=0;j<point;j++)
				print '$';
			for(int z=point+start;z<piramidSize;z++)
				print "*";
			println ''// new line
		}
	}
}
