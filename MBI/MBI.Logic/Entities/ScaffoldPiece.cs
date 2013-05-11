using System;

namespace MBI.Logic.Entities
{
	public abstract class ScaffoldPiece
	{
		public string Content { get; private set; }

		protected ScaffoldPiece(string content)
		{
			if (content == null)
			{
				throw new ArgumentNullException("content");
			}

			Content = content;
		}
	}
}
