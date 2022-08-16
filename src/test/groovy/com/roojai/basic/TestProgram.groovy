package com.roojai.basic

import com.roojai.basic.NomalClass;

class TestProgram {
	static void main(args){
		//println "Hello World"
		def testProgram = new TestProgram()
		//testProgram.weakType()
		//testProgram.StringFunc()
		//testProgram.list()
		//testProgram.map()
		//testProgram.loop()
		//testProgram.array()
		testProgram.testJava()
	}
	def weakType(){
		def a = 'John Doe'
		a = new Object()
		a = 10
		println a
	}
	def StringFunc(){
		def firstName = 'John'
		def lastName = 'Doe'
		def a = 3
		def b = 7
	
		println "${lastName}, ${firstName}"
		println "a + b = ${a + b}"
		println '${lastName}, ${firstName}'
		println 'a + b = ${a + b}'
	}
	def list(){
		List javaList = new ArrayList()		// Java
		def groovyList = []			// Groovy
		
		def alist = [5, 1, 3, 5]
		
		def myList = [10, "John", new Integer(5), new Double(7.6d)]
		
		alist = [10, 9, 8]
		alist << 7
		alist << 6
		
		alist = ["Apple", "Banana", "Carrot"]
		println alist[1]
		println alist.size()
		
		alist = [10,11,12,13,14,15,16,17,18,19,20]
		println alist.findAll{it % 2 == 0}
		
		alist = [1,2,3,4,5]
		println alist.collect{it * 10}
		
		alist = ["Apple", "Basketball", "Championship"]
		println alist*.length()
	}
	def map(){
		def emptyMap = [:]
		def mapWithValues = [fruit:'Apple',  veggie:'Carrot']
		
		def alist = [fruit:'Apple', 'veggie':'Carrot']
		alist['car'] = 'Sedan'
		alist.put('book', 'Novels')
		alist << [pet:'Dog']
		println alist['fruit']// Apple
		println alist.'veggie' // Carrot
		println alist.get('car')// Sedan
		println alist.get('book')//Novels
		println alist.get('pet')//Dog
	}
	def loop(){
		5.times {
			println "Hello"
		}
		['cat', 'dog', 'hamster'].each { name ->
			println name
		}
		(6..10).each { number ->
			println number
		}
		[fruit:'Apple', veggie:'Carrot', car:'Sedan'].each { key, value ->
			println "${key} = ${value}"
		}
	}
	def array(){
		def testArray = new String[3]
		testArray[0] = "A"
		testArray[1] = "B"
		testArray[2] = "C"
		
		String[] testArray2 = ["A", "B", "C"]
		
		String[] testArray3 = ["A", "B", "C"]
		println testArray3.length
		println testArray3.size()
		
		println testArray3[0]
		println testArray3.getAt(0)
		
		String[] testArray4 = ["A", "B", "C"]
		println testArray4[-1]
		println testArray4.getAt(-1)
		
		Integer[] intArray = [200, 300, 100]
		println intArray.min()
		String[] stringArray = ["A", "B", "C"]
		println stringArray.min()
		
		Integer[] intArray2 = [200, 300, 100]
		println intArray2.max()
		String[] stringArray2 = ["A", "B", "C"]
		println stringArray2.max()
		
		// sort
		intArray2.sort()
		println intArray2
		
		// reverse
		String[] stringArray5 = ["A", "B", "C"]
		String[] reverseArray5 = stringArray.reverse()
		println stringArray5
		println reverseArray5
		
		String[] arrs = ["A", "B", "C"]
		for(int i=0;i<arrs.size();i++){
			println arrs[i]
		}
	}
	def testJava(){
		def a = new NomalClass()
		println a.getA()
	}
}
