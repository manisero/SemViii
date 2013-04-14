using System.Collections.Generic;

namespace MBI.Logic.Extensions
{
	public static class ListExtensions
	{
		public static IEnumerable<IList<T>> GetPermutations<T>(this IList<T> list)
		{
			if (list.Count == 1)
			{
				return new List<IList<T>> { new List<T> { list[0] } };
			}

			var result = new List<IList<T>>();

			for (int i = 0; i < list.Count; i++)
			{
				var toPetmutate = new List<T>();

				for (int j = 0; j < list.Count; j++)
				{
					if (j != i)
					{
						toPetmutate.Add(list[j]);
					}
				}

				foreach (var subPermutation in toPetmutate.GetPermutations())
				{
					var permutation = new List<T> { list[i] };
					permutation.AddRange(subPermutation);

					result.Add(permutation);
				}
			}

			return result;
		}
	}
}
