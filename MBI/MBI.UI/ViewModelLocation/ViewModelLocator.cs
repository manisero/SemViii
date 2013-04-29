namespace MBI.UI.ViewModelLocation
{
	public class ViewModelLocator
	{
		public IViewModelFactory ViewModelFactory { private get; set; }

		public MainViewModel MainViewModel
		{
			get { return ViewModelFactory.Create<MainViewModel>(); }
		}
	}
}
