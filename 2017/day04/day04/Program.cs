using System;
using System.Collections.Generic;

namespace day04
{
	class MainClass
	{
		public static int test(string[] lines,Func <String,Boolean> test){
			int count = 0;
			foreach (var item in lines)
			{
				if(test(item)){
					count+=1;
				}
			}
			return count;
		}

		public static bool star7(String passphrase)
		{
			HashSet<String> wordBucket = new HashSet<string>();
			foreach (String element in passphrase.Split(null))
			{
				if (wordBucket.Contains(element))
				{
					return false;
				}
				else
				{
					wordBucket.Add(element);
				}
			}
			return true;

		}

		public static bool star8(String passphrase){
			HashSet<String> wordBucket = new HashSet<string>();
			foreach (String element in passphrase.Split(null))
			{
				if (wordBucket.Contains(element))
				{
					return false;
				}
				else
				{
					//lets generate all permutations of the word (not efficent but hey shoud work anyway)
					permute(element.ToCharArray(),0,element.Length,wordBucket);
				}
			}
			return true;
		}

	//Pointer Shupserei :) @ http://www.geeksforgeeks.org/write-a-c-program-to-print-all-permutations-of-a-given-string/
 	private static void Swap(ref char a, ref char b)
    {
        if (a == b) return;

        a ^= b;
        b ^= a;
        a ^= b;
    }

		private static void permute(char[] a,int l,int r,HashSet<String> wordBucket){
			if (l == r){
				wordBucket.Add(new string(a));	
			}
			else
			{
				for (int i = l; i < r; i++)
				{
					Swap(ref a[l] ,ref a[i]);
					permute(a, l+1, r,wordBucket);
					Swap(ref a[l] ,ref a[i]);
				}
			}
		}

		public static void Main(string[] args)
		{
			string[] lines = System.IO.File.ReadAllLines(@"input.txt");
			Console.WriteLine(test(lines,star7));
			Console.WriteLine(test(lines,star8));
		}
	}
}
