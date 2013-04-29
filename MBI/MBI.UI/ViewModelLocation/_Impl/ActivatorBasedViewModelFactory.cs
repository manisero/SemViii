using System;

namespace MBI.UI.ViewModelLocation._Impl
{
	public class ActivatorBasedViewModelFactory : IViewModelFactory
	{
		public TViewModel Create<TViewModel>()
		{
			return Activator.CreateInstance<TViewModel>();
		}
	}
}
